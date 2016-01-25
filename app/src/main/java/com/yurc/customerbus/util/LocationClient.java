package com.yurc.customerbus.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yurc.customerbus.handler.LocationHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date：1/25/2016
 * Author：yurc
 * Describe：定位类
 */
public class LocationClient implements AMapLocationListener {
    //定位类
    public AMapLocationClient aMapLocationClient = null;
    //定位配置
    public AMapLocationClientOption mLocationOption = null;

    private LocationHandler locationHandler;
    AMapLocationClientOption.AMapLocationMode MODE = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;

    public LocationClient(Context context,LocationHandler locationHandler){
        aMapLocationClient = new AMapLocationClient(context);
        this.locationHandler = locationHandler;
        //设置定位回调监听
        aMapLocationClient.setLocationListener(this);
        initOption();
    }

    public void initOption(){
        LogUtil.v("LocationClient >>>>>>>  initOption" + + Thread.currentThread().getId());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(5000);
        //给定位客户端对象设置定位参数
        aMapLocationClient.setLocationOption(mLocationOption);
    }

    public void startLocation(){
        LogUtil.v("LocationClient >>>>>>>  startLocation" + Thread.currentThread().getId());
        aMapLocationClient.startLocation();
    }

    public void stopLocation(){
        LogUtil.v("LocationClient >>>>>>>  stopLocation" + Thread.currentThread().getId());
        aMapLocationClient.stopLocation();
    }

    /**
     * 对象制空前调用
     * */
    public void onDestroy(){
        LogUtil.v("LocationClient >>>>>>>  onDestroy");
        if(aMapLocationClient != null){
            aMapLocationClient.onDestroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        LogUtil.v("LocationClient >>>>>>>  onLocationChanged" + Thread.currentThread().getId());
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LocationHandler.LOCATION_STR, amapLocation);
        msg.setData(bundle);
        msg.what = LocationHandler.END_FLAG;
        locationHandler.sendMessage(msg);
//        if (amapLocation != null) {
//            LogUtil.v("LocationClient >>>>>>> " + amapLocation.getErrorCode());
//            if (amapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码
//
//
//
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                LogUtil.e("AmapError","location Error, ErrCode:"
//                        + amapLocation.getErrorCode() + ", errInfo:"
//                        + amapLocation.getErrorInfo());
//                locationHandler.sendEmptyMessage(LocationHandler.FAIL_FLAG);
//            }
//        }else{
//            locationHandler.sendEmptyMessage(LocationHandler.FAIL_FLAG);
//        }
    }


}
