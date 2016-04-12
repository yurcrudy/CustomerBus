package com.yurc.customerbus.model;

import com.amap.api.services.busline.BusLineItem;

import java.util.List;

/**
 * Date：2016/4/10
 * Author：yurc
 * Describe：公交车线路详情
 */
public class BusLineDetail {
    private List<BusStationDetail> busStationDetailList;
    private String busLineName;
    private String busType;
    private String cityCode;
    private String busLineID;
    private String originStationName;
    private String terminalStationName;
    private String firstTime;
    private String lastTime;
    private String busCompanyName;
    private float basePrice;
    private float totalPrice;
    private float distance;

    public BusLineDetail() {
    }

    public BusLineDetail(BusLineItem busLineItem) {

        this.busLineName = busLineItem.getBusLineName();
        this.busType = busLineItem.getBusLineType();
        this.cityCode = busLineItem.getCityCode();
        this.busLineID = busLineItem.getBusLineId();
        this.originStationName = busLineItem.getOriginatingStation();
        this.terminalStationName = busLineItem.getTerminalStation();
        this.firstTime = busLineItem.getFirstBusTime().toString();
        this.lastTime = busLineItem.getLastBusTime().toString();
        this.busCompanyName = busLineItem.getBusCompany();
        this.basePrice = busLineItem.getBasicPrice();
        this.totalPrice = busLineItem.getTotalPrice();
        this.distance = busLineItem.getDistance();
    }



    public BusLineDetail(List<BusStationDetail> busStationDetailList, String busLineName, String busType, String cityCode, String busLineID, String originStationName, String terminalStationName, String firstTime, String lastTime, String busCompanyName, float basePrice, float totalPrice, float distance) {
        this.busStationDetailList = busStationDetailList;
        this.busLineName = busLineName;
        this.busType = busType;
        this.cityCode = cityCode;
        this.busLineID = busLineID;
        this.originStationName = originStationName;
        this.terminalStationName = terminalStationName;
        this.firstTime = firstTime;
        this.lastTime = lastTime;
        this.busCompanyName = busCompanyName;
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
        this.distance = distance;
    }

    public List<BusStationDetail> getBusStationDetailList() {
        return busStationDetailList;
    }

    public void setBusStationDetailList(List<BusStationDetail> busStationDetailList) {
        this.busStationDetailList = busStationDetailList;
    }

    public String getBusLineName() {
        return busLineName;
    }

    public void setBusLineName(String busLineName) {
        this.busLineName = busLineName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBusLineID() {
        return busLineID;
    }

    public void setBusLineID(String busLineID) {
        this.busLineID = busLineID;
    }

    public String getOriginStationName() {
        return originStationName;
    }

    public void setOriginStationName(String originStationName) {
        this.originStationName = originStationName;
    }

    public String getTerminalStationName() {
        return terminalStationName;
    }

    public void setTerminalStationName(String terminalStationName) {
        this.terminalStationName = terminalStationName;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getBusCompanyName() {
        return busCompanyName;
    }

    public void setBusCompanyName(String busCompanyName) {
        this.busCompanyName = busCompanyName;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
