package com.yurc.customerbus.model;

import com.amap.api.services.busline.BusStationItem;

import java.io.Serializable;

/**
 * Date：4/12/2016
 * Author：yurc
 * Describe：公交站点数据查询
 */
public class BusStationDetail implements Serializable{
    private String stationID;
    private String busStationName;
    private String cityCode;
    private String ADCode;

    public BusStationDetail() {
    }

    public BusStationDetail(BusStationItem busStationItem) {
        this.stationID = busStationItem.getBusStationId();
        this.busStationName = busStationItem.getBusStationName();
        this.cityCode = busStationItem.getCityCode();
        this.ADCode = busStationItem.getAdCode();
    }



    public BusStationDetail(String stationID, String busStationName, String cityCode, String ADCode) {
        this.stationID = stationID;
        this.busStationName = busStationName;
        this.cityCode = cityCode;
        this.ADCode = ADCode;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getBusStationName() {
        return busStationName;
    }

    public void setBusStationName(String busStationName) {
        this.busStationName = busStationName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getADCode() {
        return ADCode;
    }

    public void setADCode(String ADCode) {
        this.ADCode = ADCode;
    }
}
