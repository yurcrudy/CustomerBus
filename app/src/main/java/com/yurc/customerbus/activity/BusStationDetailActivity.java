package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusStationLinesAdapter;
import com.yurc.customerbus.model.BusLineDetail;
import com.yurc.customerbus.model.BusStationDetail;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;
import com.yurc.customerbus.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date：4/29/2016
 * Author：yurc
 * Describe：站点详情界面
 */
public class BusStationDetailActivity extends BaseActivity implements View.OnClickListener,BusLineSearch.OnBusLineSearchListener{
    private BusStationDetail busStationDetail;
    private TextView tv_bus_station_name;
    private ImageView iv_back;
    private ListViewForScrollView lv_bus_line;
    private BusStationLinesAdapter busStationLinesAdapter;
    private List<BusLineDetail> busLineDetailList;
    private int currentpage;
    private BusLineQuery busLineQuery;// 公交线路查询的查询类
    private String cityCode; //城市代码
    private BusLineSearch busLineSearch;// 公交线路列表查询
    private BusLineResult busLineResult;// 公交线路搜索返回的结果
    private String busLineName;
    private String busLineNameReal;
    private BusLineDetail busLineDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station_detail);
        initViews();
    }

    public void initViews(){
        tv_bus_station_name = (TextView)findViewById(R.id.tv_bus_station_name);
        lv_bus_line = (ListViewForScrollView)findViewById(R.id.lv_bus_line);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusStationDetailActivity.this);
        busStationDetail = (BusStationDetail)getIntent().getSerializableExtra("BUS_STATION_DETAIL");
        busLineDetailList = new ArrayList<BusLineDetail>();
        busStationLinesAdapter = new BusStationLinesAdapter(busLineDetailList,R.layout.list_item_bus_station_lines,BusStationDetailActivity.this);
        lv_bus_line.setAdapter(busStationLinesAdapter);
        lv_bus_line.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                busLineDetail = busLineDetailList.get(i);
                busLineNameReal = busLineDetail.getBusLineName();
                if(busLineNameReal.contains("路")){
                    busLineName = busLineNameReal.substring(0,busLineNameReal.indexOf("路"));
                }else{
                    busLineName = busLineNameReal.substring(0,2);
                }
                searchLine(busLineName);

            }
        });
        if(busStationDetail != null){
            tv_bus_station_name.setText(busStationDetail.getBusStationName());
            if(busStationDetail.getBusLineDetailList() != null && busStationDetail.getBusLineDetailList().size() > 0){
                Map<String,BusLineDetail> map = new HashMap<String,BusLineDetail>();
                for(BusLineDetail busLineDetail : busStationDetail.getBusLineDetailList()){
                    map.put(busLineDetail.getBusLineName(),busLineDetail);
                }
                List<String> list = new ArrayList<String>();
                list.addAll(map.keySet());
                Collections.sort(list);
                for(String key : list){
                    busLineDetailList.add(map.get(key));
                }
                busStationLinesAdapter.notifyDataSetChanged();
            }
        }else{

        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onBusLineSearched(BusLineResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getQuery() != null
                    && result.getQuery().equals(busLineQuery)) {
                if (result.getQuery().getCategory() == BusLineQuery.SearchType.BY_LINE_NAME) {
                    if (result.getPageCount() > 0
                            && result.getBusLines() != null
                            && result.getBusLines().size() > 0) {
                        List<BusLineItem> lineItems = result.getBusLines();
                        boolean judge = true;
                        for(BusLineItem busLineItem : lineItems){
                            if(busLineNameReal.equals(busLineItem.getBusLineName())){
                                busLineDetail = new BusLineDetail(busLineItem);
                                judge = false;
                                Intent intent = new Intent(BusStationDetailActivity.this, BusLineDetailActivity.class);
                                intent.putExtra(BusLineDetailActivity.BUSLINE_DETAIL, busLineDetail);
                                startActivity(intent);
                                break;
                            }
                        }
                        if(judge){
                            ToastUtil.showForShort(BusStationDetailActivity.this,"没有搜索到该线路的数据..请重试");
                        }
                    } else {
                        ToastUtil.showForShort(BusStationDetailActivity.this, R.string.no_result);
                    }
                }else{
                    ToastUtil.showForShort(BusStationDetailActivity.this, R.string.no_result);
                }
            }
        } else if (rCode == 27) {
            ToastUtil.showForShort(BusStationDetailActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.showForShort(BusStationDetailActivity.this, R.string.error_key);
        } else {
            ToastUtil.showForShort(BusStationDetailActivity.this, R.string.error_other);
        }
        dismissDialog();
    }

    /**
     * 公交线路搜索
     */
    public void searchLine(String search) {
        currentpage = 0;// 第一页默认从0开始
        showDialog("正在搜索....");

        cityCode = SharedPerferenceUtil.getString(BusStationDetailActivity.this, DictionaryUtil.CITY_CODE, "440400");
        busLineQuery = new BusLineQuery(search, BusLineQuery.SearchType.BY_LINE_NAME, cityCode);// 第一个参数表示公交线路名，第二个参数表示公交线路查询，第三个参数表示所在城市名或者城市区号
        busLineQuery.setPageSize(10);// 设置每页返回多少条数据
        busLineQuery.setPageNumber(currentpage);// 设置查询第几页，第一页从0开始算起
        busLineSearch = new BusLineSearch(this, busLineQuery);// 设置条件
        busLineSearch.setOnBusLineSearchListener(this);// 设置查询结果的监听
        busLineSearch.searchBusLineAsyn();// 异步查询公交线路名称
        // 公交站点搜索事例
    }
}
