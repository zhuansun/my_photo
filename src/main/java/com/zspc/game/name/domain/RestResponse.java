package com.zspc.game.name.domain;

public class RestResponse {


    private Integer code = 200;

    private String msg = "SUCCESS";

    private Object data;

    public Integer getCode() {
        return code;
    }

    public RestResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RestResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
