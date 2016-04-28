package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusSurroundAdapter;
import com.yurc.customerbus.dao.BusLine;
import com.yurc.customerbus.handler.LocationHandler;
import com.yurc.customerbus.impl.LocationImpl;
import com.yurc.customerbus.model.BusSurround;
import com.yurc.customerbus.model.Location;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/1
 * Author：yurc
 * Describe：周边公交页面
 */
public class SurroundBusActivity extends BaseActivity implements View.OnClickListener,LocationImpl,PoiSearch.OnPoiSearchListener {

    private ListView lv_bus;
    private BusSurroundAdapter busSurroundAdapter;
    private ImageView iv_back;
    //todo 完善定位功能
    private LocationHandler locationHandler;
    private AMapLocation location;
    private TextView tv_title;
    private int currentPage;
    private String cityCode;
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private PoiResult poiResult; // poi返回的结果
    private List<BusSurround> busSurroundList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_bus);
        initViews();
    }

    public void initViews(){

        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setOnClickListener(SurroundBusActivity.this);
        lv_bus = (ListView)findViewById(R.id.lv_bus);


        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(SurroundBusActivity.this);
        locationHandler = new LocationHandler(SurroundBusActivity.this,SurroundBusActivity.this);
        locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);
        busSurroundList = new ArrayList<BusSurround>();
        busSurroundAdapter = new BusSurroundAdapter(busSurroundList,SurroundBusActivity.this,R.layout.list_item_surround_bus);
        lv_bus.setAdapter(busSurroundAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_title:
                locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);
                break;

        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showDialog("正在搜索");// 显示进度框
        currentPage = 0;
        cityCode = SharedPerferenceUtil.getString(SurroundBusActivity.this, DictionaryUtil.CITY_CODE, "0756");
        query = new PoiSearch.Query("公交站", "公交车站", cityCode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(15);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(location.getLatitude(),location.getLongitude()),2000));

        poiSearch.searchPOIAsyn();
    }

    @Override
    public void startLocation() {
        showDialog("正在定位....");
        tv_title.setEnabled(false);
    }

    @Override
    public void stopLocation() {
        dismissDialog();
    }

    @Override
    public void finishLocation(AMapLocation amapLocation) {
        dismissDialog();
        if(amapLocation!= null){
            location = amapLocation;
            if(amapLocation.getAddress().contains("市")){
                tv_title.setText(amapLocation.getAddress().substring(amapLocation.getAddress().indexOf("市") + 1));
                doSearchQuery();
            }
        } else{
            tv_title.setText("定位失败，请重新定位.");
        }
        tv_title.setEnabled(true);
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
                        //todo
                        Location mlocation = new Location(location.getLatitude(),location.getLongitude());
                        busSurroundList.clear();
                        for(PoiItem item : poiItems){
                            BusSurround busSurround = new BusSurround(item,mlocation);
                            busSurroundList.add(busSurround);
                        }
                        busSurroundAdapter.notifyDataSetChanged();
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {

                    } else {
                        ToastUtil.showForShort(SurroundBusActivity.this, R.string.no_result);
                    }
                }

            } else {
                ToastUtil.showForShort(SurroundBusActivity.this,
                        R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.showForShort(SurroundBusActivity.this,
                    R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.showForShort(SurroundBusActivity.this, R.string.error_key);
        } else {
            ToastUtil.showForShort(SurroundBusActivity.this,
                    getString(R.string.error_other) + rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
