package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusLineDetailStationListAdapter;
import com.yurc.customerbus.model.BusLineDetail;
import com.yurc.customerbus.model.BusStationDetail;
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
    private static final String BUSLINE_DETAIL = "BUSLINE_DETAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busline_detail);
        Intent intent = getIntent();
        busLineDetail = (BusLineDetail)intent.getSerializableExtra(BUSLINE_DETAIL);
        if(busLineDetail !=  null){
            initViews();
        }

    }

    public void initViews(){
        busStationDetailList = new ArrayList<BusStationDetail>();
        busStationDetailList.addAll(busLineDetail.getBusStationDetailList());
        tv_bus_line_number = (TextView)findViewById(R.id.tv_bus_line_number);
        tv_bus_line_number.setText(busLineDetail.getBusLineName().substring(0,busLineDetail.getBusLineName().indexOf("(")));
        tv_bus_line_prace = (TextView)findViewById(R.id.tv_bus_line_prace);
        tv_bus_line_prace.setText(busLineDetail.getTotalPrice() + "元");
        tv_bus_line_time = (TextView)findViewById(R.id.tv_bus_line_time);

        lv_bus_station = (ListViewForScrollView)findViewById(R.id.lv_bus_station);
        busLineDetailStationListAdapter = new BusLineDetailStationListAdapter(R.layout.list_item_bus_station,busStationDetailList,BusLineDetailActivity.this);
        lv_bus_station.setAdapter(busLineDetailStationListAdapter);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusLineDetailActivity.this);

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
