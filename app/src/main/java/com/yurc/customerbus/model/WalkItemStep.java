package com.yurc.customerbus.model;

import com.amap.api.services.route.WalkStep;

import java.io.Serializable;

/**
 * Date：4/24/2016
 * Author：yurc
 * Describe：路径规划阶段性方案中的步行项中的步行阶段性方案
 */
public class WalkItemStep implements Serializable{
    private float distance; //距离
    private String describe;//描述
    public WalkItemStep(WalkStep walkStep) {
        this.describe = walkStep.getInstruction();
        this.distance = walkStep.getDistance();
    }

    public WalkItemStep(float distance, String describe) {
        this.distance = distance;
        this.describe = describe;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
