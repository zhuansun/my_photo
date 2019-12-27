package com.zspc.photo.my_photo;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
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

    private static final String[] VERIFICATIONS = {"11111", "22222", "33333", "44444", "55555"};

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
            response.setMsg("用户名或密码或验证码不能为空");
            return response;
        }

        //校验是否是邮箱地址
        if (!REGEX.matcher(userName).matches()) {
            response.setCode(101);
            response.setMsg("请输入正确的邮箱");
            return response;
        }

        if (!Arrays.asList(VERIFICATIONS).contains(verification)) {
            response.setCode(102);
            response.setMsg("请输入正确的验证码");
            return response;
        }

        String context = "用户名:" + userName + "<br/>" + "密码:" + password;
        boolean sendBy163 = MailUtils.sendBy163("zhuansunpengcheng@qq.com", "又来新订单了", context);

        if (sendBy163) {
            response.setCode(200);
            response.setMsg("提交成功，请等待处理");
            return response;
        }

        response.setCode(103);
        response.setMsg("未知错误");
        return response;
    }

}
