package com.zspc.photo.my_photo;

import java.io.File;
import java.io.IOException;

/**
 * 用txt文件充当数据库
 *
 * @author : zhuansun
 * @date : 2019-12-27 09:29
 **/
public class FileDbUtils {


    public static File loadFile(String path) {
        File file=null;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static File loadVeriFile(){
//        String path = "/root/submit/db/verification.txt";
        String path = "/Users/zhuansun/Desktop/verification.txt";
        return FileDbUtils.loadFile(path);
    }


    public static File loadUsedVeriFile(){
//        String path = "/root/submit/db/usedVerification.txt";
        String path = "/Users/zhuansun/Desktop/usedVerification.txt";
        return FileDbUtils.loadFile(path);
    }

}
