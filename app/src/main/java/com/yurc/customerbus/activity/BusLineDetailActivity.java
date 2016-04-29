package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusLineDetailStationListAdapter;
import com.yurc.customerbus.dao.BusLineDetailDB;
import com.yurc.customerbus.dao.controller.BusLineDetailDBController;
import com.yurc.customerbus.model.BusLineDetail;
import com.yurc.customerbus.model.BusStationDetail;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;
import com.yurc.customerbus.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/4
 * Author：yurc
 * Describe：线路详情
 */
public class BusLineDetailActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_bus_line_number;
    private TextView tv_bus_line_prace;
    private TextView tv_bus_line_time;
    private ListViewForScrollView lv_bus_station;
    private List<BusStationDetail> busStationDetailList;
    private BusLineDetailStationListAdapter busLineDetailStationListAdapter;
    private BusLineDetail busLineDetail = null;
    private ImageView iv_back;
    public static final String BUSLINE_DETAIL = "BUSLINE_DETAIL";
    private TextView tv_title;
    private BusLineDetailDBController busLineDetailDBController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busline_detail);
        Intent intent = getIntent();
        busLineDetail = (BusLineDetail)intent.getSerializableExtra(BUSLINE_DETAIL);
        initViews();
    }

    public void initViews(){
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setOnClickListener(BusLineDetailActivity.this);
        tv_title.setFocusable(true);
        tv_title.setFocusableInTouchMode(true);
        tv_title.requestFocus();
        busStationDetailList = new ArrayList<BusStationDetail>();
        lv_bus_station = (ListViewForScrollView)findViewById(R.id.lv_bus_station);
        busLineDetailStationListAdapter = new BusLineDetailStationListAdapter(R.layout.list_item_bus_station,busStationDetailList,BusLineDetailActivity.this);
        lv_bus_station.setAdapter(busLineDetailStationListAdapter);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusLineDetailActivity.this);
            busStationDetailList.addAll(busLineDetail.getBusStationDetailList());
            busLineDetailStationListAdapter.notifyDataSetChanged();
            tv_bus_line_number = (TextView)findViewById(R.id.tv_bus_line_number);
            tv_bus_line_number.setText(busLineDetail.getBusLineName().substring(0, busLineDetail.getBusLineName().indexOf("(")));
            tv_bus_line_prace = (TextView)findViewById(R.id.tv_bus_line_prace);
            tv_bus_line_prace.setText(busLineDetail.getTotalPrice() + "元");
            tv_bus_line_time = (TextView)findViewById(R.id.tv_bus_line_time);
            if(busLineDetail.getFirstTime() != null && busLineDetail.getFirstTime()!=null){
                tv_bus_line_time.setText(busLineDetail.getFirstTime() + "-" + busLineDetail.getLastTime());
            }
        addToDB();
    }

    public void addToDB(){
        busLineDetailDBController = BusLineDetailDBController.getInstance(BusLineDetailActivity.this);
        BusLineDetailDB busLineDetailDB = new BusLineDetailDB();
        busLineDetailDB.setCity(SharedPerferenceUtil.getString(BusLineDetailActivity.this, DictionaryUtil.CITY_NAME,"珠海市"));
        busLineDetailDB.setBusLineName(busLineDetail.getBusLineName());
        busLineDetailDB.setBusLineDetail(JsonUtil.toJson(busLineDetail));
        busLineDetailDBController.addBusLineDetailDB(busLineDetailDB);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }


}
