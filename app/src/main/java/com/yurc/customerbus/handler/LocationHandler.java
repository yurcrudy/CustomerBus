package com.yurc.customerbus.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.yurc.customerbus.impl.LocationImpl;
import com.yurc.customerbus.util.LocationClient;

/**
 * Date：1/25/2016
 * Author：yurc
 * Describe：启动别的线程执行
 */
public class LocationHandler extends Handler {

    public static String LOCATION_STR = "LOCATION";
    public static final int START_FLAG = 1;
    public static final int STOP_FLAG = 2;
    public static final int END_FLAG = 3;
    public static final int DESTROY_FLAG = 4;
    private Context context;
    private LocationImpl location;
    private LocationClient locationClient;
    public LocationHandler(Context context,LocationImpl location){
        this.location = location;
        this.context = context;
        this.locationClient = new LocationClient(context,this);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case START_FLAG:
                location.startLocation();
                locationClient.startLocation();
                break;
            case STOP_FLAG:
                location.stopLocation();
                locationClient.stopLocation();
                break;
            case END_FLAG:
                Bundle bundle = msg.getData();
                AMapLocation aMapLocation = bundle.getParcelable(LOCATION_STR);
                location.finishLocation(aMapLocation);
                break;
            case DESTROY_FLAG:
                locationClient.onDestroy();
                break;
        }
    }
}
