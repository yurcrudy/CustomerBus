package com.yurc.customerbus.model;

import com.amap.api.services.core.LatLonPoint;

import java.io.Serializable;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：位置类
 */
public class Location implements Serializable{
    private double longitude;
    private double latitude;

    public Location(LatLonPoint latLonPoint) {
        this.latitude = latLonPoint.getLatitude();
        this.longitude = latLonPoint.getLongitude();

    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
