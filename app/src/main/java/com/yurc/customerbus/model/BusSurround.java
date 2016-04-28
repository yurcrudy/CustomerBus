package com.yurc.customerbus.model;

import com.amap.api.services.core.PoiItem;

import java.io.Serializable;

/**
 * Date：4/28/2016
 * Author：yurc
 * Describe：周边公交实体类
 */
public class BusSurround implements Serializable{
    private BusStationDetail busStationDetail;
    private float distance;//距离
    private Location location;//当前位置点

    public BusSurround(PoiItem item,Location location) {
        if(item != null){
            this.location = location;
            this.distance = item.getDistance();
            this.busStationDetail = new BusStationDetail();
            this.busStationDetail.setLocation(new Location(item.getLatLonPoint()));
            this.busStationDetail.setBusStationName(item.getTitle());
            this.busStationDetail.setADCode(item.getAdCode());
            this.busStationDetail.setSnippet(item.getSnippet());
        }
    }

    public BusStationDetail getBusStationDetail() {
        return busStationDetail;
    }

    public void setBusStationDetail(BusStationDetail busStationDetail) {
        this.busStationDetail = busStationDetail;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
