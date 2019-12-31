package com.zspc.photo.my_photo;

/**
 * 提交异常
 *
 * @author : zhuansun
 * @date : 2019-12-31 10:22
 **/
public class SubmitException extends RuntimeException{

    private String code = "100";

    private String msg;

    public static final String SUBMIT_EXCEPTION_ERROR_EMAIL = "邮箱错误";
    public static final String SUBMIT_EXCEPTION_ERROR_VERIFICATION = "验证码错误";
    public static final String SUBMIT_EXCEPTION_ERROR_PARAMS = "邮箱密码验证码不能为空";
    public static final String SUBMIT_EXCEPTION_ERROR_SEND = "邮件发送失败";

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SubmitException(String msg) {
        super(msg);
        this.msg = msg;
    }


}
