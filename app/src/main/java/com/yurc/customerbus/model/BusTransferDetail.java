package com.yurc.customerbus.model;

import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：公交车的换乘方案详情类
 */
public class BusTransferDetail implements Serializable{
    private float duration;//方案预计时间
    private float busDistance;//公交行驶的距离
    private float cost;//公交换乘方案的花费
    private float distance;//方案总的距离
    private float walkDistance;//总步行的距离
    private boolean nightBus;//返回是否包含夜班车
    private List<BusTransferStep> busTransferStepList = new ArrayList<BusTransferStep>();//公交换乘阶段集合
    private int transferNum = 0;//该方案换乘的次数
    private int passStationNum = 0;//该方案经过公交站的数量

    public BusTransferDetail(BusPath busPath) {
        this.busDistance = busPath.getBusDistance();
        this.cost = busPath.getCost();
        this.distance = busPath.getDistance();
        this.walkDistance = busPath.getWalkDistance();
        this.duration = busPath.getDuration();
        this.nightBus = busPath.isNightBus();
        for(BusStep busStep : busPath.getSteps()){
            busTransferStepList.add(new BusTransferStep(busStep));
            transferNum += busStep.getBusLines().size();
        }
        if(transferNum > 0){
            transferNum--;
        }
        for(BusTransferStep busTransferStep : busTransferStepList){
            passStationNum += busTransferStep.getPassStationNum();
        }
    }

    public BusTransferDetail() {
    }


    public float getBusDistance() {
        return busDistance;
    }

    public void setBusDistance(float busDistance) {
        this.busDistance = busDistance;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(float walkDistance) {
        this.walkDistance = walkDistance;
    }

    public boolean isNightBus() {
        return nightBus;
    }

    public void setNightBus(boolean nightBus) {
        this.nightBus = nightBus;
    }

    public List<BusTransferStep> getBusTransferStepList() {
        return busTransferStepList;
    }

    public void setBusTransferStepList(List<BusTransferStep> busTransferStepList) {
        this.busTransferStepList = busTransferStepList;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(int transferNum) {
        this.transferNum = transferNum;
    }

    public int getPassStationNum() {
        return passStationNum;
    }

    public void setPassStationNum(int passStationNum) {
        this.passStationNum = passStationNum;
    }
}
