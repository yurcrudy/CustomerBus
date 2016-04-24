package com.yurc.customerbus.model;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.RouteBusLineItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：路径规划阶段性方案
 */
public class BusTransferStep implements Serializable{
    private WalkItem walkItem;
    private List<BusTransferBusItem> busTransferBusItems = new ArrayList<BusTransferBusItem>();

    public BusTransferStep(BusStep busStep) {
        this.walkItem = new WalkItem(busStep.getWalk());
        for(RouteBusLineItem routeBusLineItem : busStep.getBusLines()){

        }
    }

    public BusTransferStep() {
    }

    public WalkItem getWalkItem() {
        return walkItem;
    }

    public void setWalkItem(WalkItem walkItem) {
        this.walkItem = walkItem;
    }

    public List<BusTransferBusItem> getBusTransferBusItems() {
        return busTransferBusItems;
    }

    public void setBusTransferBusItems(List<BusTransferBusItem> busTransferBusItems) {
        this.busTransferBusItems = busTransferBusItems;
    }
}
