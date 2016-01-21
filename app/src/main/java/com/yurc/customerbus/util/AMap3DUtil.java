package com.yurc.customerbus.util;


import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

/**
 * Date：1/21/2016
 * Author：yurc
 * Describe：
 */
public class AMap3DUtil {

    private static AMap aMap = null;

    private AMap3DUtil(){
        throw new AssertionError();
    }


    public static AMap initAMap(MapView mapView){
        aMap = (mapView == null)? null:mapView.getMap();
        return  aMap;
    }

}
