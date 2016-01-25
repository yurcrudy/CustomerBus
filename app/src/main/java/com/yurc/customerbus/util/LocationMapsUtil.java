package com.yurc.customerbus.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.yurc.customerbus.application.BusApplication;
import com.yurc.customerbus.handler.LocationHandler;
import com.yurc.customerbus.impl.LocationImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date：1/22/2016
 * Author：yurc
 * Describe：3d地图定位工具类 + 监听 + 定位层
 *
 */
public class LocationMapsUtil implements LocationSource,LocationImpl {
    private LocationHandler locationHandler;
    private LocationSource.OnLocationChangedListener mListener;
    private Context context;
    private AMapLocationClientOption.AMapLocationMode LOCATION_MODE = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;//默认定位方式

    public LocationMapsUtil(Context context){
        this.context = context;
        initHandler();
    }
    public LocationMapsUtil(Context context,AMapLocationClientOption.AMapLocationMode mode){
        this.context = context;
        this.LOCATION_MODE = mode;
        initHandler();
    }

    public void initHandler(){
        if(locationHandler == null){
            locationHandler = new LocationHandler(context,this);
        }
    }
    /**
     * LocationSource 激活定位层
     * */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        LogUtil.v("LocationMapsUtil >>>>>> activate");
        mListener = onLocationChangedListener;
        initHandler();
        locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);//启动定位
    }

    @Override
    public void deactivate() {
        LogUtil.v("LocationMapsUtil >>>>>> deactivate");
        mListener = null;
        onDestroy();
    }

    public void onDestroy(){
        LogUtil.v("LocationMapsUtil >>>>>> onDestroy");
        locationHandler.sendEmptyMessage(LocationHandler.DESTROY_FLAG);
        locationHandler = null;
    }

    /**
     * 开始定位
     * */
    @Override
    public void startLocation(){
        LogUtil.v("LocationMapsUtil >>>>>> startLocation");
    }
    //停止
    @Override
    public void stopLocation() {
        LogUtil.v("LocationMapsUtil >>>>>> stopLocation");
    }
    //完成定位
    @Override
    public void finishLocation(AMapLocation amapLocation) {
            LogUtil.v("LocationMapsUtil  >>>>>> onLocationChanged");
                if (amapLocation != null) {
                    LogUtil.v("LocationClient >>>>>>> " + amapLocation.getErrorCode());
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        df.format(date);//定位时间
                        amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        amapLocation.getCountry();//国家信息
                        amapLocation.getProvince();//省信息
                        amapLocation.getCity();//城市信息
                        amapLocation.getDistrict();//城区信息
                        amapLocation.getStreet();//街道信息
                        amapLocation.getStreetNum();//街道门牌号信息
                        amapLocation.getCityCode();//城市编码
                        amapLocation.getAdCode();//地区编码
                        if (mListener != null) {
                            mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                        }
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        LogUtil.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
    }
}
