package com.yurc.customerbus.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.yurc.customerbus.application.BusApplication;

/**
 * Date：1/22/2016
 * Author：yurc
 * Describe：3d地图定位工具类 + 监听 + 定位层
 *
 */
public class LocationMapsUtil implements LocationSource,AMapLocationListener {

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private Context context;
    private AMapLocationClientOption.AMapLocationMode LOCATION_MODE = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;//默认定位方式
    private boolean NEED_ADDRESS = true;//
    private boolean ONCE_LOCATION = true;//是否只定位一次
    public LocationMapsUtil(Context context){
        this.context = context;
    }

    public LocationMapsUtil(Context context,AMapLocationClientOption.AMapLocationMode mode){
        this.context = context;
        this.LOCATION_MODE = mode;
    }

    /**
     * 启动定位
     * */
    public void startLocation(){
        if(mlocationClient == null){
            initLocationClient();
        }

    }
    /**
     * 初始化定位参数变量
     * */
    public void initLocationClient(){
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(context);
            initLocationClientOption();
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        }
        mlocationClient.startLocation();//开始定位
    }

    /**
     * 初始化定位参数变量
     * */
    public void initLocationClientOption(){
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置为高精度定位模式
        mLocationOption.setLocationMode(LOCATION_MODE);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(NEED_ADDRESS);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(ONCE_LOCATION);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
    }



    /**
     * LocationSource 定位成功后回调该方法
     * */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        LogUtil.v("LocationMapsUtil >>>>>> onLocationChanged");
        if (mListener != null && aMapLocation != null) {
            LogUtil.v(String.valueOf(aMapLocation.getErrorCode()));
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                LogUtil.v(aMapLocation.toString());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
        //只做一次定位
        mlocationClient.stopLocation();
    }
    /**
     * LocationSource 激活定位层
     * */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        LogUtil.v("LocationMapsUtil >>>>>> activate");
        mListener = onLocationChangedListener;
        initLocationClient();
        mlocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        LogUtil.v("LocationMapsUtil >>>>>> deactivate");
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    public void onDestroy(){
        LogUtil.v("LocationMapsUtil >>>>>> onDestroy");
        if(mlocationClient != null){
            mlocationClient.onDestroy();
        }
    }

}
