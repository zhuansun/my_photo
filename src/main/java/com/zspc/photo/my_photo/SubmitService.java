package com.zspc.photo.my_photo;

public interface SubmitService {

    boolean checkVeri(String verification);

    boolean handleSubmit(String userName, String password, String verification);

    boolean handleSuccess(String userName);

    boolean checkEmail(String userName);
}
