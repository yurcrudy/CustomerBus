package com.yurc.customerbus.model;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private Location location;
    private String snippet;
    private List<BusLineDetail> busLineDetailList = new ArrayList<BusLineDetail>();
    public BusStationDetail() {
    }

    public BusStationDetail(BusStationItem busStationItem) {
        this.stationID = busStationItem.getBusStationId();
        this.busStationName = busStationItem.getBusStationName();
        this.cityCode = busStationItem.getCityCode();
        this.ADCode = busStationItem.getAdCode();
        this.location = new Location(busStationItem.getLatLonPoint());
        this.snippet = "";
        if(busStationItem.getBusLineItems()!=null && busStationItem.getBusLineItems().size() > 0){
            for(BusLineItem busLineItem:busStationItem.getBusLineItems()){
                busLineDetailList.add(new BusLineDetail(busLineItem));
                if(busLineItem.getBusLineName().contains("路")){
                    snippet += busLineItem.getBusLineName().substring(0,busLineItem.getBusLineName().indexOf("路") + 1) + ";";
                }
            }
            this.snippet.substring(0,this.snippet.length() - 1);
        }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public List<BusLineDetail> getBusLineDetailList() {
        return busLineDetailList;
    }

    public void setBusLineDetailList(List<BusLineDetail> busLineDetailList) {
        this.busLineDetailList = busLineDetailList;
    }
}
