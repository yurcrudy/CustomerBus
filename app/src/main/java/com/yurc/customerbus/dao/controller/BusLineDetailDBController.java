package com.yurc.customerbus.dao.controller;

import android.content.Context;

import com.yurc.customerbus.dao.BusLineDetailDB;
import com.yurc.customerbus.dao.BusLineDetailDBDao;
import com.yurc.customerbus.dao.DaoSession;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Date：2016/4/29
 * Author：yurc
 * Describe：
 */
public class BusLineDetailDBController {

    private BusLineDetailDBDao busLineDetailDBDao;
    private static Context context;
    private static BusLineDetailDBController busLineDetailDBController;

    public BusLineDetailDBController(){}

    public static BusLineDetailDBController getInstance(Context mContext){
        if(busLineDetailDBController == null){
            busLineDetailDBController = new BusLineDetailDBController();
            if(context == null){
                context = mContext;
            }
            DaoSession daoSession = GreenDaoController.getDaoSession(context);
            busLineDetailDBController.busLineDetailDBDao = daoSession.getBusLineDetailDBDao();
        }
        return busLineDetailDBController;
    }

    /**
     * 添加线路详情数据库列
     * */
    public boolean addBusLineDetailDB(BusLineDetailDB busLineDetailDB){
        boolean flag = false;//用于判断是否添加成功
        try {
            QueryBuilder<BusLineDetailDB> qb = busLineDetailDBDao.queryBuilder();
            qb.where(BusLineDetailDBDao.Properties.City.like(busLineDetailDB.getCity()))
                    .where(BusLineDetailDBDao.Properties.BusLineName.like(busLineDetailDB.getBusLineName()));
            if(qb.list() == null || qb.list().size() == 0){
                busLineDetailDBDao.insert(busLineDetailDB);
            }
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询 所有线路详情数据库列
     * */
    public List<BusLineDetailDB> getBusLineDetailDBList(){
        return busLineDetailDBDao.loadAll();
    }


    /**
     * 根据城市来获得线路详情类
     * 获取集合长度最长为5
     *
     * */
    public List<BusLineDetailDB> getLimitNameList(String city){
        QueryBuilder<BusLineDetailDB> qb = busLineDetailDBDao.queryBuilder();
        qb.orderDesc(BusLineDetailDBDao.Properties.Id)
                .where(BusLineDetailDBDao.Properties.City.like(city))
                .limit(5);
        return qb.list();
    }



    /**
     * 获取单个答题提醒对象获取的id值
     * */
/*    public AnswerRemind getId(AnswerRemind answerRemind){
        return answerRemindDao.getKey(answerRemind);
    }*/
    /**
     * 删除 单个答题提醒 根据AnswerRemind
     * */
    public boolean delete(BusLineDetailDB busLineDetailDB){
        boolean flag = false;//用于判断是否删除成功
        try {
            busLineDetailDBDao.delete(busLineDetailDB);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 删除 单个答题提醒 根据AnswerRemind
     * */
    public boolean deleteList(List<BusLineDetailDB> busLineDetailDBs){
        boolean flag = false;//用于判断是否删除成功
        try {
            if(busLineDetailDBs != null && busLineDetailDBs.size() > 0){
                for(BusLineDetailDB busLineDetailDB : busLineDetailDBs){
                    busLineDetailDBDao.delete(busLineDetailDB);
                }
            }

            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }




    /**
     * 根据id判断是否存在
     * */
    public boolean isSaved(Long id){
        QueryBuilder<BusLineDetailDB> qb = busLineDetailDBDao.queryBuilder();
        qb.where(BusLineDetailDBDao.Properties.Id.eq(id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }

    /**
     * 更新 更新实体类信息
     * */
    public boolean update(BusLineDetailDB busLineDetailDB){
        boolean flag = false;//用于判断是否更新成功
        try {
            busLineDetailDBDao.update(busLineDetailDB);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除  删除所有实体类信息
     * */
    public boolean deleteAll(){
        boolean flag =  false;
        try{
            busLineDetailDBDao.deleteAll();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();

        }
        return flag;
    }
}
