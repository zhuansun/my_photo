package com.zspc.photo.db.database;

/**
 * 数据库实现
 *
 * @author : zhuansun
 * @date : 2019-12-27 09:59
 **/
public interface DataBase<T> {

    void execute(String sql);


    T executeQuery(String sql);

}
