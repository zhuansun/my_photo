package com.zspc.photo.my_photo;

import com.zspc.photo.db.database.FileDataBase;
import com.zspc.photo.db.operation.FileDbOperation;

/**
 * 用txt文件充当数据库
 *
 * @author : zhuansun
 * @date : 2019-12-27 09:29
 **/
public class FileDbUtils {

    private static FileDbOperation dbOperation;

    static {
        dbOperation = new FileDbOperation(new FileDataBase());
    }


    void insert(String data){
        dbOperation.insert(data);
    }


    String search(String data){
        return dbOperation.search(data);
    }



}
