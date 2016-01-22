package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.yurc.customerbus.R;
import com.yurc.customerbus.application.BusApplication;
import com.yurc.customerbus.util.AMapMapsUtil;
import com.yurc.customerbus.util.LocationMapsUtil;
import com.yurc.customerbus.util.LogUtil;

/**
 * Date：1/21/2016
 * Author：yurc
 * Describe：3d地图定位页面
 */
public class LocationMapsActivity extends BaseActivity{
    private Button btn_test;
    private final double lt= 22.371533;
    private final double pt= 113.573098;
    private MapView mapView;
    private AMap aMap;
    private LocationMapsUtil locationMapsUtil;//定位监听类

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_maps);
        mapView = (MapView)findViewById(R.id.mv_location);
        btn_test = (Button)findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationMapsUtil == null){
                    locationMapsUtil = new LocationMapsUtil(getApplicationContext());
                }
            }
        });
        mapView.onCreate(savedInstanceState);
        init();
    }

    void init(){
        LogUtil.v("init");
        LogUtil.v(BusApplication.sHA1(this));
        if(aMap == null){
            aMap = AMapMapsUtil.initAMap(mapView);
            initLocation();
        }
    }
    /**
     * 初始化定位层
     * */
    void initLocation(){
        if(locationMapsUtil == null){
            locationMapsUtil = new LocationMapsUtil(LocationMapsActivity.this);
        }
        //初始化定位层，并触发定位
        AMapMapsUtil.startLocation(aMap, locationMapsUtil);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        locationMapsUtil.deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationMapsUtil.onDestroy();
    }

}
