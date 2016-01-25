package com.yurc.customerbus.util;


import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.yurc.customerbus.R;


/**
 * Date：1/21/2016
 * Author：yurc
 * Describe：3D地图工具类
 */
public class AMapMapsUtil {

    private static AMap aMap = null;
    private static LatLng latLng = null;//位置点
    private static float zoom = 16.0f;//默认地图显示级别
    private static double longitude = 0;//默认地图中心的经度
    private static double position = 0;//默认地图中心的纬度
    private static MyLocationStyle myLocationStyle;//位置点
    private static boolean LOCATION_BUTTON = true;//地图上的定位按钮默认显示
    private static boolean LOCATION_ENABLE = true;//是否触发定位，默认触发，可以配置
    private AMapMapsUtil(){
        throw new AssertionError();
    }

    public static AMap initAMap(MapView mapView){

        aMap = (mapView == null)? null:mapView.getMap();
        if(aMap == null){
            return aMap;
        }
        if(latLng == null){
            latLng = new LatLng(longitude,position);
        }
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if(myLocationStyle == null){
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
        }
        aMap.setMyLocationStyle(myLocationStyle);
        return  aMap;
    }

    public static AMap initAMap(MapView mapView,LatLng lat){
        setLatLng(lat);
        return  initAMap(mapView);
    }

    public static AMap initAMap(MapView mapView,double lon,double pos){
        setLongitude(lon);
        setPosition(pos);
        return  initAMap(mapView,new LatLng(longitude,position));
    }

    /**
     * 初始化地图定位层
     * */
    public static boolean startLocation(AMap aMap,LocationMapsUtil locationMapsUtil){
        if(aMap != null && locationMapsUtil != null){
            aMap.setLocationSource(locationMapsUtil);
            aMap.getUiSettings().setMyLocationButtonEnabled(LOCATION_BUTTON);//设置定位按钮
            aMap.setMyLocationEnabled(LOCATION_ENABLE);
            return true;
        }
        return false;
    }

    public static boolean startLocation(AMap aMap,LocationMapsUtil locationMapsUtil,boolean locationEnable){
        if(aMap != null && locationMapsUtil != null){
            aMap.setLocationSource(locationMapsUtil);
            aMap.getUiSettings().setMyLocationButtonEnabled(LOCATION_BUTTON);//设置定位按钮
            aMap.setMyLocationEnabled(locationEnable);
            return true;
        }
        return false;
    }


    public static void setLatLng(LatLng latLng) {
        AMapMapsUtil.latLng = latLng;
    }

    public static void setZoom(float zoom) {
        AMapMapsUtil.zoom = zoom;
    }

    public static void setLongitude(double longitude) {
        AMapMapsUtil.longitude = longitude;
    }

    public static void setPosition(double position) {
        AMapMapsUtil.position = position;
    }

    public static void setMyLocationStyle(MyLocationStyle myLocationStyle) {
        AMapMapsUtil.myLocationStyle = myLocationStyle;
    }

    public static void setLocationEnable(boolean locationEnable) {
        LOCATION_ENABLE = locationEnable;
    }
}
