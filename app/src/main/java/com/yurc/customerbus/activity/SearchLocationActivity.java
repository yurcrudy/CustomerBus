package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.model.Location;
import com.yurc.customerbus.util.AMapMapsUtil;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.LocationMapsUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.StringUtil;
import com.yurc.customerbus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/17
 * Author：yurc
 * Describe：寻找位置页面
 */
public class SearchLocationActivity extends BaseActivity implements View.OnClickListener,PoiSearch.OnPoiSearchListener,AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener{
    private ImageView iv_search;
    private EditText et_location_name;
    private final double lt= 22.371533;
    private final double pt= 113.573098;
    private MapView mapView;
    private AMap aMap;
    private LocationMapsUtil locationMapsUtil;//定位监听类
    private String cityCode = "";
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private String keyWord = "";
    private ImageView iv_back;
    private ArrayList<MarkerOptions> markerOptionsList = new ArrayList<MarkerOptions>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        mapView = (MapView)findViewById(R.id.mv_location);
        mapView.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews(){
        if(aMap == null){
            aMap = AMapMapsUtil.initAMap(mapView);
            initLocation();
        }
        iv_back = (ImageView)findViewById(R.id.iv_back);

        iv_back.setOnClickListener(SearchLocationActivity.this);
        iv_search = (ImageView)findViewById(R.id.iv_search);
        iv_search.setOnClickListener(SearchLocationActivity.this);
        et_location_name = (EditText)findViewById(R.id.et_location_name);

    }

    /**
     * 初始化定位层
     * */
    public void initLocation(){
        if(locationMapsUtil == null){
            locationMapsUtil = new LocationMapsUtil(SearchLocationActivity.this);
        }
        //初始化定位层，并触发定位
        AMapMapsUtil.startLocation(aMap, locationMapsUtil);
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
                keyWord = et_location_name.getText().toString().trim();
                if(!StringUtil.isBlank(keyWord)){
                    doSearchQuery();
                }else{
                }

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_address:

                break;
        }
    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showDialog("正在搜索");// 显示进度框
        currentPage = 0;
        cityCode = SharedPerferenceUtil.getString(SearchLocationActivity.this, DictionaryUtil.CITY_CODE, "0756");
        query = new PoiSearch.Query(keyWord, "地名地址信息", cityCode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(15);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        dismissDialog();// 隐藏对话框
        if (rCode == 0) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    LogUtil.v(JsonUtil.toJson(poiItems));
                    if (poiItems != null && poiItems.size() > 0) {
                        aMap.clear();// 清理之前的图标
                        addMarkersToMap(poiItems);
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {

                    } else {
                        ToastUtil.showForShort(SearchLocationActivity.this, R.string.no_result);
                    }
                }
            } else {
                ToastUtil.showForShort(SearchLocationActivity.this,
                        R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.showForShort(SearchLocationActivity.this,
                    R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.showForShort(SearchLocationActivity.this, R.string.error_key);
        } else {
            ToastUtil.showForShort(SearchLocationActivity.this,
                    getString(R.string.error_other) + rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
    /**
     *  在地图上添加marker
     * */
    private void addMarkersToMap(List<PoiItem> poiItems){
        View view = null;
        if(poiItems != null && !poiItems.isEmpty()){
            markerOptionsList.clear();
            for(PoiItem item : poiItems){
                view = LayoutInflater.from(SearchLocationActivity.this).inflate(R.layout.location_marker,null);
                TextView tv_address = (TextView)view.findViewById(R.id.tv_address);
                tv_address.setOnClickListener(SearchLocationActivity.this);
                tv_address.setText(item.getTitle());
                MarkerOptions markerOption = new MarkerOptions().anchor(0.5f, 0.5f)
                        .title(item.getTitle())
                        .position(new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude()))
                        .icon(BitmapDescriptorFactory.fromView(view))
                        .draggable(false).period(50);
                markerOptionsList.add(markerOption);

            }
            aMap.addMarkers(markerOptionsList,true);
        }else{
            ToastUtil.showForShort(SearchLocationActivity.this, "未搜索到相关数据");
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        ToastUtil.showForShort(SearchLocationActivity.this, marker.getSnippet() + "onInfoWindowClick");
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent();
        intent.putExtra("LOCATION",marker.getTitle());
        intent.putExtra("LOCATION_DETAIL", new Location(marker.getPosition().longitude,marker.getPosition().latitude));
        setResult(1, intent);
        finish();
        return true;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    //
//    /**
//     * 在地图上添加marker
//     */
//    private void addMarkersToMap() {
//        //文字显示标注，可以设置显示内容，位置，字体大小颜色，背景色旋转角度
//        TextOptions textOptions = new TextOptions().position(Constants.BEIJING)
//                .text("Text").fontColor(Color.BLACK)
//                .backgroundColor(Color.BLUE).fontSize(30).rotate(20).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
//                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD)
//                ;
//        aMap.addText(textOptions);
//
//        Marker marker = aMap.addMarker(new MarkerOptions()
//
//                .title("好好学习")
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
//                .draggable(true));
//        marker.setRotateAngle(90);// 设置marker旋转90度
//        marker.setPositionByPixels(400, 400);
//        marker.showInfoWindow();// 设置默认显示一个infowinfow
//
//        markerOption = new MarkerOptions();
//        markerOption.position(Constants.XIAN);
//        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
//
//        markerOption.draggable(true);
//        markerOption.icon(
//                // BitmapDescriptorFactory
//                // .fromResource(R.drawable.location_marker)
//                BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                        .decodeResource(getResources(),
//                                R.drawable.location_marker)));
//        // 将Marker设置为贴地显示，可以双指下拉看效果
//        markerOption.setFlat(true);
//
//        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//        giflist.add(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//        giflist.add(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        giflist.add(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
//
//        MarkerOptions markerOption1 = new MarkerOptions().anchor(0.5f, 0.5f)
//                .position(Constants.CHENGDU).title("成都市")
//                .snippet("成都市:30.679879, 104.064855").icons(giflist)
//                .draggable(true).period(50);
//        ArrayList<MarkerOptions> markerOptionlst = new ArrayList<MarkerOptions>();
//        markerOptionlst.add(markerOption);
//        markerOptionlst.add(markerOption1);
//        List<Marker> markerlst = aMap.addMarkers(markerOptionlst, true);
//        marker2 = markerlst.get(0);
//    }
//
//    /**
//     * 对marker标注点点击响应事件
//     */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        if (marker.equals(marker2)) {
//            if (aMap != null) {
//                jumpPoint(marker);
//            }
//        }
//        markerText.setText("你点击的是" + marker.getTitle());
//        return false;
//    }

}
