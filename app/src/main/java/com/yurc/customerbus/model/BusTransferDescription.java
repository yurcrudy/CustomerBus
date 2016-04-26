package com.yurc.customerbus.model;

import java.io.Serializable;

/**
 * Date：4/26/2016
 * Author：yurc
 * Describe：线路换乘描述
 */
public class BusTransferDescription implements Serializable {
    private String description;//描述
    private String detail;//详情
    private boolean judgeBus;//true为公交车
    private String startOrEnd = null;//起点或者终点标志
    public BusTransferDescription(String description, String detail,boolean judgeBus) {
        this.description = description;
        this.detail = detail;
        this.judgeBus = judgeBus;
    }

    public BusTransferDescription(String description, String detail, boolean judgeBus, String startOrEnd) {
        this.description = description;
        this.detail = detail;
        this.judgeBus = judgeBus;
        this.startOrEnd = startOrEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isJudgeBus() {
        return judgeBus;
    }

    public void setJudgeBus(boolean judgeBus) {
        this.judgeBus = judgeBus;
    }

    public String getStartOrEnd() {
        return startOrEnd;
    }

    public void setStartOrEnd(String startOrEnd) {
        this.startOrEnd = startOrEnd;
    }
}
