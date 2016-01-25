package com.yurc.customerbus.impl;

import com.amap.api.location.AMapLocation;

/**
 * Date：1/25/2016
 * Author：yurc
 * Describe：定位回调接口
 */
public interface LocationImpl {
    //开始
    public void startLocation();
    //停止
    public void stopLocation();
    //完成
    public void finishLocation(AMapLocation amapLocation);




}
