package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusStationQueryAdapter;
import com.yurc.customerbus.model.BusStationDetail;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：4/15/2016
 * Author：yurc
 * Describe：站点查询页面
 */
public class BusStationQueryActivity extends BaseActivity implements View.OnClickListener,BusStationSearch.OnBusStationSearchListener{
    private ImageView iv_back;
    private EditText et_bus_station_name;
    private ListView lv_history;
    private LinearLayout ll_del;
    private ImageView iv_search;
    private int currentpage = 0;
    private String cityCode;
    private BusStationQuery busStationQuery;
    private String search = "";
    private List<BusStationDetail> busStationDetailList;
    private BusStationQueryAdapter busStationQueryAdapter;
    private LinearLayout ll_city;
    private TextView tv_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_station_query);
        initViews();

    }

    public void initViews(){
        busStationDetailList = new ArrayList<BusStationDetail>();
        busStationQueryAdapter = new BusStationQueryAdapter(busStationDetailList,BusStationQueryActivity.this,R.layout.list_item_bus_station_query);
        iv_search = (ImageView)findViewById(R.id.iv_search);
        iv_search.setOnClickListener(BusStationQueryActivity.this);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusStationQueryActivity.this);
        et_bus_station_name = (EditText)findViewById(R.id.et_bus_station_name);
        lv_history = (ListView)findViewById(R.id.lv_history);
        lv_history.setAdapter(busStationQueryAdapter);
        ll_del = (LinearLayout)findViewById(R.id.ll_del);
        ll_del.setOnClickListener(BusStationQueryActivity.this);
        tv_city = (TextView)findViewById(R.id.tv_city);
        ll_city = (LinearLayout)findViewById(R.id.ll_city);
        ll_city.setOnClickListener(BusStationQueryActivity.this);
        tv_city.setText(SharedPerferenceUtil.getString(BusStationQueryActivity.this, DictionaryUtil.CITY_NAME, "珠海市"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_del:
                ToastUtil.showForShort(BusStationQueryActivity.this, "清空");
                break;
            case R.id.iv_search:
                searchStation();
                break;
            case R.id.ll_city:
                Intent intent = new Intent(BusStationQueryActivity.this,CityListActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }


    public void searchStation(){
        currentpage = 0;// 第一页默认从0开始
        showDialog("正在搜索....");
        search = et_bus_station_name.getText().toString().trim();
        if ("".equals(search)) {
            search = "北理工南门";
            et_bus_station_name.setText(search);
        }

        cityCode = SharedPerferenceUtil.getString(BusStationQueryActivity.this, DictionaryUtil.CITY_CODE, "440400");

         busStationQuery = new BusStationQuery(search,cityCode);
         busStationQuery.setPageSize(10);
         busStationQuery.setPageNumber(currentpage);
		 BusStationSearch busStationSearch = new BusStationSearch(this,busStationQuery);
         busStationSearch.setOnBusStationSearchListener(this);
		 busStationSearch.searchBusStationAsyn();
    }

    @Override
    public void onBusStationSearched(BusStationResult busStationResult, int code) {
        if (code == 0) {
            if (busStationResult != null && busStationResult.getQuery() != null
                    && busStationResult.getQuery().equals(busStationQuery)) {
                if (busStationResult.getQuery().getQueryString().equals(search)) {
                    LogUtil.v(JsonUtil.toJson(busStationResult.getBusStations()));
                    showResultList(busStationResult.getBusStations());
                }
            } else {
                ToastUtil.showForShort(BusStationQueryActivity.this, R.string.no_result);
            }
        } else if (code == 27) {
            ToastUtil.showForShort(BusStationQueryActivity.this, R.string.error_network);
        } else if (code == 32) {
            ToastUtil.showForShort(BusStationQueryActivity.this, R.string.error_key);
        } else {
            ToastUtil.showForShort(BusStationQueryActivity.this, R.string.error_other);
        }
        dismissDialog();
    }

    public void showResultList(List<BusStationItem> items){
        if(items != null && !items.isEmpty()){
            busStationDetailList.clear();
            ll_del.setVisibility(View.GONE);
            for(BusStationItem item : items){
                busStationDetailList.add(new BusStationDetail(item));
            }
            busStationQueryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == DictionaryUtil.FINISH_CITY_LIST){
            tv_city.setText(SharedPerferenceUtil.getString(BusStationQueryActivity.this, DictionaryUtil.CITY_NAME, "珠海市"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
