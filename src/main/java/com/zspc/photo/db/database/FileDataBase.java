package com.zspc.photo.db.database;

/**
 * @author : zhuansun
 * @date : 2019-12-27 10:20
 **/
public class FileDataBase implements DataBase<String>{

    @Override
    public void execute(String data) {
        //清空文件，重新把 data 写进去


    }

    @Override
    public String executeQuery(String sql) {

        //加载文件，返回所有的文件内容

        return null;
    }
}
