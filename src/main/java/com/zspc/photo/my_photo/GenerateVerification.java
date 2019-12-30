package com.zspc.photo.my_photo;

import java.util.UUID;

public class GenerateVerification {


    public static void main(String[] args) {
        genOneMonth(10);
    }


    public static void genOneMonth(int count){
        //一个月
        for (int i = 0 ; i < count ; i++){
            String uuid = "OM"+UUID.randomUUID().toString().toUpperCase().replace("-","");
            System.out.println(uuid);
        }
    }


    public static void genOneYear(int count){
        //一个月
        for (int i = 0 ; i < count ; i++){
            String uuid = "OY"+UUID.randomUUID().toString().toUpperCase().replace("-","");
            System.out.println(uuid);
        }
    }


}
