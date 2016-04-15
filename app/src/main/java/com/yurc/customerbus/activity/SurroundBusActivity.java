package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusLineListAdapter;
import com.yurc.customerbus.dao.BusLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/1
 * Author：yurc
 * Describe：周边公交页面
 */
public class SurroundBusActivity extends BaseActivity implements View.OnClickListener{
    private List<BusLine> busLinelist;
    private ListView lv_bus;
    private BusLineListAdapter busLineListAdapter;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround_bus);
        initViews();
    }

    public void initViews(){
        busLinelist = new ArrayList<BusLine>();
        BusLine bus = new BusLine();
        bus.setLinename("72A");
        bus.setDirectionStie("北理工站");
        bus.setDirectionStie("唐家湾城规站");
        bus.setDistance("400");
        bus.setStartTime("08:00");
        bus.setEndTime("22:00");
        busLinelist.add(bus);
        bus = new BusLine();
        bus.setLinename("10A");
        bus.setDirectionStie("北理工站");
        bus.setDirectionStie("唐家湾城规站");
        bus.setDistance("400");
        bus.setStartTime("06:00");
        bus.setEndTime("21:00");
        busLinelist.add(bus);
        busLineListAdapter = new BusLineListAdapter(busLinelist,SurroundBusActivity.this,R.layout.list_item_surround_bus);
        lv_bus = (ListView)findViewById(R.id.lv_bus);
        lv_bus.setAdapter(busLineListAdapter);

        iv_back = (ImageView)findViewById(R.id.iv_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public void searchBusLine(){
    }
}
