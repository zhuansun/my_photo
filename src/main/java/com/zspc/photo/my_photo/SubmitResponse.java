package com.zspc.photo.my_photo;

/**
 * @author : zhuansun
 * @date : 2019-12-25 11:27
 **/
public class SubmitResponse {

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SubmitResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static SubmitResponse buildSuccess(){
        return new SubmitResponse("200","提交成功");
    }

    public static SubmitResponse buildError(String code , String msg){
        return new SubmitResponse(code,msg);
    }

    public static SubmitResponse buildUnkownError(){
        return new SubmitResponse("101","未知异常");
    }

}
