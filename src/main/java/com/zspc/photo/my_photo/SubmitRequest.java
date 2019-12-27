package com.zspc.photo.my_photo;

/**
 * @author : zhuansun
 * @date : 2019-12-25 09:24
 **/
public class SubmitRequest {

    private String username;

    private String password;

    private String verification;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
