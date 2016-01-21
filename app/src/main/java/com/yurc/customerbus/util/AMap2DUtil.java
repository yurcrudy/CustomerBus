package com.yurc.customerbus.util;


import android.graphics.Color;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.yurc.customerbus.R;

/**
 * Date：1/21/2016
 * Author：yurc
 * Describe：2D地图工具类
 */
public class AMap2DUtil {
    private static AMap aMap = null;
    private static LatLng latLng = null;//位置点
    private static float zoom = 15.0f;//默认地图显示级别
    private static double longitude = 0;
    private static double position = 0;
    private static MyLocationStyle myLocationStyle;//位置点
    //不允许初始化
    private AMap2DUtil(){
        throw new AssertionError();
    }

    public static AMap initAMap(MapView mapView){
        aMap = (mapView == null)? null:mapView.getMap();
        if(latLng != null){
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        }
        if(myLocationStyle == null){
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
//            myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//            myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
//            // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//            myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        }
        aMap.setMyLocationStyle(myLocationStyle);
        return aMap;
    }

    public static AMap initAMap(MapView mapView,LatLng lat){
        setLatLng(lat);
        return initAMap(mapView);
    }

    public static AMap initAMap(MapView mapView,double lon,double pos){
        setLongitude(lon);
        setPosition(pos);
        return initAMap(mapView,new LatLng(longitude,position));
    }



    /**
     * 设置地图显示级别
     * */
    public static void setZoom(float z) {
        if (z >= 3 && z <= 20) {
            zoom = z;
        }
    }

    /**
     * 设置地图默认位置中心点
     * */
    public static void setLatLng(LatLng latLng) {
        AMap2DUtil.latLng = latLng;
    }
    /**
     * 设置返回地址中心点经度
     * */
    public static void setLongitude(double lon) {
        longitude = lon;
    }
    /**
     * 设置返回地址中心点的纬度
     * */
    public static void setPosition(double pos) {
        position = pos;
    }
}
