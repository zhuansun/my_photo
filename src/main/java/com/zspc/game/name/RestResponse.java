package com.zspc.game.name;

import java.util.List;

public class RestResponse {


    private Integer code = 200;

    private String msg = "SUCCESS";

    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public RestResponse setData(List<String> data) {
        this.data = data;
        return this;
    }
}
