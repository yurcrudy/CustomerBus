package com.yurc.customerbus.dao.controller;

import android.content.Context;

import com.yurc.customerbus.dao.BusLine;
import com.yurc.customerbus.dao.BusLineDao;
import com.yurc.customerbus.dao.DaoSession;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


/**
 * Description 答题提醒数据表 控制类
 * Author   YRC
 * DateTime 2015/7/31
 */
public class BusLineController {

    private BusLineDao busLineDao;
    private static Context context;
    private static BusLineController busLineController;

    public BusLineController(){}

    public static BusLineController getInstance(Context mContext){
        if(busLineController == null){
            busLineController = new BusLineController();
            if(context == null){
                context = mContext;
            }
            DaoSession daoSession = GreenDaoController.getDaoSession(context);
            busLineController.busLineDao = daoSession.getBusLineDao();
        }
        return busLineController;
    }

    /**
     * 添加 答题提醒数据
     * */
    public boolean addBusLine(BusLine busLine){
        boolean flag = false;//用于判断是否添加成功
        try {
            busLineDao.insert(busLine);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询 所有答题提醒数据
     * */
    public List<BusLine> getBusLineList(){
        return busLineDao.loadAll();
    }

//    /**
//     * 根据时间来获得答题提醒列表
//     * 根据小时、分钟数来升序排序
//     * 获取所有正在开启的数据
//     * */
//    public List<BusLine> getLimitTimeList(){
//        QueryBuilder<AnswerRemind> qb = answerRemindDao.queryBuilder();
//        qb.orderAsc(AnswerRemindDao.Properties.Hour)
//          .orderAsc(AnswerRemindDao.Properties.Minute)
//          .where(AnswerRemindDao.Properties.Status.eq(true));
//        return qb.list();
//    }

    /**
     * 根据时间来获得答题提醒列表
     * 根据小时、分钟数来升序排序
     * 获取所有正在开启的数据
     * */
    public List<BusLine> getLimitNameList(String name){
        QueryBuilder<BusLine> qb = busLineDao.queryBuilder();
        qb.orderAsc(BusLineDao.Properties.Linename)
          .where(BusLineDao.Properties.Linename.like("%" + name + "%"));
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
    public boolean delete(BusLine busLine){
        boolean flag = false;//用于判断是否删除成功
        try {
            busLineDao.delete(busLine);
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
        QueryBuilder<BusLine> qb = busLineDao.queryBuilder();
        qb.where(BusLineDao.Properties.Id.eq(id));
        qb.buildCount().count();
        return qb.buildCount().count() > 0 ? true : false;
    }

    /**
     * 更新 更新实体类信息
     * */
    public boolean update(BusLine busLine){
        boolean flag = false;//用于判断是否更新成功
        try {
            busLineDao.update(busLine);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 更新 更新集合信息 更新提醒对象
     * */
    public boolean updateStatus(List<BusLine> list){
        boolean flag = false;//用于判断是否更新成功
        try {
            for(BusLine ar : list){
                busLineDao.update(ar);
            }
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
            busLineDao.deleteAll();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();

        }
        return flag;
    }


}
