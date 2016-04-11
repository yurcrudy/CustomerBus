package com.yurc.customerbus.dao.controller;

import android.content.Context;


import com.yurc.customerbus.dao.DaoMaster;
import com.yurc.customerbus.dao.DaoSession;

import java.util.List;


import de.greenrobot.dao.query.QueryBuilder;

/**
 * Description GreenDao控制类
 * Author   YRC
 * DateTime 2015/7/31
 */
public class GreenDaoController {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 获得DaoMaster
     * */
    public static DaoMaster getDaoMaster(Context context){
        if(daoMaster == null){
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,"notes-db",null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }
    /**
     * 返回 DaoSession
     * */
    public static DaoSession getDaoSession(Context context){
        if(daoSession == null){
            if(daoMaster == null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }













}
