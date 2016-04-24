package com.yurc.customerbus.model;

import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.WalkStep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：路径规划阶段性方案中的步行项
 */
public class WalkItem implements Serializable{
    private Location detination;//终点位置
    private Location origin;//起始位置
    private long duration;//时间
    private List<WalkItemStep> walkItemStepList = new ArrayList<WalkItemStep>();//步行阶段性方案

    public WalkItem() {
    }
    public WalkItem(RouteBusWalkItem routeBusWalkItem) {
        this.detination = new Location(routeBusWalkItem.getDestination());
        this.origin = new Location(routeBusWalkItem.getOrigin());
        this.duration = routeBusWalkItem.getDuration();
        for(WalkStep walkStep : routeBusWalkItem.getSteps()){
            walkItemStepList.add(new WalkItemStep(walkStep));
        }
    }

    public Location getDetination() {
        return detination;
    }

    public void setDetination(Location detination) {
        this.detination = detination;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public List<WalkItemStep> getWalkItemStepList() {
        return walkItemStepList;
    }

    public void setWalkItemStepList(List<WalkItemStep> walkItemStepList) {
        this.walkItemStepList = walkItemStepList;
    }
}
