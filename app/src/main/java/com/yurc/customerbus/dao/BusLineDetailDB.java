package com.yurc.customerbus.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table BUS_LINE_DETAIL_DB.
 */
public class BusLineDetailDB {

    private Long id;
    private String BusLineDetail;
    private String City;
    private String BusLineName;

    public BusLineDetailDB() {
    }

    public BusLineDetailDB(Long id) {
        this.id = id;
    }

    public BusLineDetailDB(Long id, String BusLineDetail, String City, String BusLineName) {
        this.id = id;
        this.BusLineDetail = BusLineDetail;
        this.City = City;
        this.BusLineName = BusLineName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusLineDetail() {
        return BusLineDetail;
    }

    public void setBusLineDetail(String BusLineDetail) {
        this.BusLineDetail = BusLineDetail;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getBusLineName() {
        return BusLineName;
    }

    public void setBusLineName(String BusLineName) {
        this.BusLineName = BusLineName;
    }

}
