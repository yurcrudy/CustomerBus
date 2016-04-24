package com.yurc.customerbus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：路径规划阶段性方案中的公交车项
 */
public class BusTransferBusItem extends BusLineDetail implements Serializable{
    private BusStationDetail arrivalBusStation;//到达站点
    private BusStationDetail departureBusStation;//出发站点
    private float duration;//该阶段预计行驶时间
    private int passStaionNum;//经过站点的数目
    private List<Location> polyLine;//经过站点的位置

    public BusTransferBusItem() {
    }

    public BusStationDetail getArrivalBusStation() {
        return arrivalBusStation;
    }

    public void setArrivalBusStation(BusStationDetail arrivalBusStation) {
        this.arrivalBusStation = arrivalBusStation;
    }

    public BusStationDetail getDepartureBusStation() {
        return departureBusStation;
    }

    public void setDepartureBusStation(BusStationDetail departureBusStation) {
        this.departureBusStation = departureBusStation;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getPassStaionNum() {
        return passStaionNum;
    }

    public void setPassStaionNum(int passStaionNum) {
        this.passStaionNum = passStaionNum;
    }

    public List<Location> getPolyLine() {
        return polyLine;
    }

    public void setPolyLine(List<Location> polyLine) {
        this.polyLine = polyLine;
    }
}
