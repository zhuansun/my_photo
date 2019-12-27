package com.zspc.photo.db.operation;

import com.zspc.photo.db.database.DataBase;

/**
 * 抽象的DB操作
 *
 * @author : zhuansun
 * @date : 2019-12-27 09:58
 **/
public class AbstractDbOperation<Q> {

    private DataBase<Q> dataBase;

    AbstractDbOperation(DataBase<Q> dataBase) {
        this.dataBase = dataBase;
    }

    public void insert(String data){
        dataBase.execute(data);
    }

    public void delete(String data){
        dataBase.execute(data);
    }

    public void update(String data){
        dataBase.execute(data);
    }

    public Q search(String data){
        return dataBase.executeQuery(data);
    }

}
