package com.zspc.photo.my_photo;

import org.springframework.util.ResourceUtils;

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
//            file = ResourceUtils.getFile("classpath:" + path);
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


}
