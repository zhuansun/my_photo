package com.zspc.photo.my_photo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author : zhuansun
 * @date : 2019-12-31 10:56
 **/
@Service
public class SubmitServiceImpl implements SubmitService {

    private static final Logger logger = LoggerFactory.getLogger(SubmitServiceImpl.class);

    private static final Pattern REGEX = Pattern.compile(
        "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 校验验证码是否存在
     */
    @Override
    public boolean checkVeri(String veri) {
        List<String> notUsedVerification = readVeri();
        if (!CollectionUtils.isEmpty(notUsedVerification)) {
            return notUsedVerification.contains(veri);
        }
        return false;
    }

    /**
     * 处理请求
     */
    @Override
    public boolean handleSubmit(String user, String password, String veri) {

        //给我发邮件
        boolean toMe = sendReceiveToMe(user, password, veri);
        if (toMe) {
            //给用户发邮件
            boolean toUser = sendReceiveToUser(user, password, veri);
            if (toUser) {
                //记录验证码对应的邮箱
                writeRecode(user, password, veri);
                //回写验证码
                writeVeri(user, password, veri);
                return true;
            }
        }
        return false;
    }

    /**
     * 处理成功请求
     */
    @Override
    public boolean handleSuccess(String user) {
        sendSuccessToUser(user);
        return true;
    }

    private boolean sendReceiveToUser(String user, String pass, String veri) {
        String toUserContext =
            "<br/>用户名:" + user + "<br/><br/>" + "密码:" + pass + "<br/><br/>" + "验证码:" + veri
                + "<br/><br/>您的订单预计<b>5小时</b>内处理完成，处理成功后会有邮件通知！<br/><br/> 请耐心等待";
        try {
            MailUtils.sendBy126(user, "您的订单正在处理中", toUserContext);
        } catch (MessagingException e) {
            logger.error("给用户发新订单邮件报错:user:{},pass:{},veri:{},ex:{}", user, pass, veri, e);
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_SEND);
        }
        return true;
    }

    private boolean sendReceiveToMe(String user, String pass, String veri) {
        String toMeContext =
            "<br/>用户名:" + user + "<br/><br/>" + "密码:" + pass + "<br/><br/>" + "验证码:" + veri;
        try {
            MailUtils.sendBy126("zhuansunpengcheng@qq.com", "又来新订单了", toMeContext);
        } catch (MessagingException e) {
            logger.error("给我发新订单邮件报错:user:{},pass:{},veri:{},ex:{}", user, pass, veri, e);
            throw new SubmitException(SubmitException.SUBMIT_EXCEPTION_ERROR_SEND);
        }
        return true;
    }

    private void sendSuccessToUser(String user) {
        String toUserContext =
            "<br/>用户名:" + user + "<br/><br/>" + "<br/><br/>您的订单已经处理完成，请登录查看！<br/><br/> 建议修改密码";
        try {
            MailUtils.sendBy126(user, "您的订单已完成", toUserContext);
        } catch (MessagingException e) {
            logger.error("给用户发成功邮件报错:user:{},ex:{}", user, e);
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkEmail(String userName) {
        return REGEX.matcher(userName).matches();
    }

    /**
     * 读取所有没有使用的验证码
     */
    private List<String> readVeri() {
        File file = FileDbUtils.loadVeriFile();
        List<String> notUsedVerification = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                notUsedVerification.add(s);
            }
            br.close();
        } catch (Exception e) {
            logger.error("读取所有没有使用的验证码..error..失败:{}", e);
        }
        return notUsedVerification;
    }

    /**
     * 重写验证码
     */
    private boolean writeVeri(String user, String pass, String veri) {
        List<String> notUsedVerification = readVeri();
        if (!CollectionUtils.isEmpty(notUsedVerification)) {
            notUsedVerification.remove(veri);
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FileDbUtils.loadVeriFile()));
            if (!CollectionUtils.isEmpty(notUsedVerification)) {
                for (String s : notUsedVerification) {
                    bw.write(s);
                    bw.newLine();
                    bw.flush();
                }
            }
            bw.close();
        } catch (IOException e) {
            logger.error("重写验证码失败.....user:{},pass:{},veri:{},ex:{}", user, pass, veri, e);
            return false;
        }

        return true;
    }

    /**
     * 将验证码记录到已使用的文件
     */
    private boolean writeRecode(String user, String pass, String veri) {
        File file = FileDbUtils.loadUsedVeriFile();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.newLine();
            bw.write(user + "-" + pass + "-" + "-" + veri);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            logger.error("将验证码记录到已使用的文件失败.....user:{},pass:{},veri:{},ex:{}", user, pass, veri, e);
            return false;
        }
        return true;
    }

}
