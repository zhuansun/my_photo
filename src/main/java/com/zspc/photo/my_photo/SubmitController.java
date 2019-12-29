package com.zspc.photo.my_photo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试controlelr
 **/
@Controller
public class SubmitController {

    private static final Pattern REGEX = Pattern.compile(
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    private static final String[] VERIFICATIONS = {"666666"};

    /**
     * 跳转到项目首页
     */
    @RequestMapping("/index")
    public String test() {
        return "/index";
    }

    /**
     * 开始上传
     */
    @RequestMapping("/submit")
    @ResponseBody
    public SubmitResponse submit(@RequestBody SubmitRequest submitVo) {

        SubmitResponse response = new SubmitResponse();

        String userName = submitVo.getUsername();
        String password = submitVo.getPassword();
        String verification = submitVo.getVerification();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils
                .isEmpty(verification)) {
            response.setCode(100);
            response.setMsg("提交失败,用户名或密码或验证码不能为空");
            return response;
        }

        //校验是否是邮箱地址
        if (!REGEX.matcher(userName).matches()) {
            response.setCode(101);
            response.setMsg("提交失败,请输入正确的邮箱");
            return response;
        }

        if (!checkVeri(verification)) {
            response.setCode(102);
            response.setMsg("提交失败,请输入正确的验证码");
            return response;
        } else {
            writeRecode(userName, verification);
        }


        boolean submit = handleSubmit(userName, password, verification);

        if (submit) {
            response.setCode(200);
            response.setMsg("提交成功,请等待处理");
            return response;
        }


        response.setCode(103);
        response.setMsg("提交失败,未知错误");
        return response;
    }


    /**
     * 校验验证码是否存在
     */
    private boolean checkVeri(String veri) {

        String path = ClassLoader.getSystemResource("db/verification.txt").getPath();

        File file = FileDbUtils.loadFile(path);

        List<String> notUsedVerification = new ArrayList<>();

        boolean check = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                notUsedVerification.add(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!CollectionUtils.isEmpty(notUsedVerification)) {
            if (notUsedVerification.contains(veri)) {
                check = true;
                notUsedVerification.remove(veri);
            }
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            if (!CollectionUtils.isEmpty(notUsedVerification)) {
                for (String s : notUsedVerification) {
                    bw.write(s);
                    bw.newLine();
                    bw.flush();
                }
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }


    /**
     * 将验证码写入文件
     */

    public void writeRecode(String user, String veri) {

        String path = ClassLoader.getSystemResource("db/usedVerification.txt").getPath();

        File file = FileDbUtils.loadFile(path);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.newLine();
            bw.write(veri + "-" + user);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理请求
     */
    private boolean handleSubmit(String user, String password, String veri) {


        //给我发邮件
        String toMeContext = "<br/>用户名:" + user + "<br/><br/>" + "密码:" + password + "<br/><br/>" + "验证码:" + veri;
        boolean toMe = MailUtils.sendBy163("zhuansunpengcheng@qq.com", "又来新订单了", toMeContext);

        //给用户发邮件
        String toUserContext = "<br/>用户名:" + user + "<br/><br/>" + "密码:" + password + "<br/><br/>" + "验证码:" + veri + "<br/><br/>您的订单预计<b>5小时</b>内处理完成，处理成功后会有邮件通知！<br/><br/> 请耐心等待";
        boolean toUser = MailUtils.sendBy163(user, "您的订单正在处理中", toUserContext);


        return toMe && toUser;
    }

}
