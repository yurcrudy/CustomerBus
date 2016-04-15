package com.yurc.customerbus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yurc.customerbus.R;

/**
 * Date：3/24/2016
 * Author：yurc
 * Describe：APP 首页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_surround_bus;
    private RelativeLayout rl_line_bus;
    private RelativeLayout rl_transfer_bus;
    private RelativeLayout rl_station_bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
    }

    /**
     * 初始化空间用
     * */
    public void initView(){
        rl_surround_bus = (RelativeLayout)findViewById(R.id.rl_surround_bus);
        rl_surround_bus.setOnClickListener(HomeActivity.this);
        rl_line_bus = (RelativeLayout)findViewById(R.id.rl_line_bus);
        rl_line_bus.setOnClickListener(HomeActivity.this);
        rl_transfer_bus = (RelativeLayout)findViewById(R.id.rl_transfer_bus);
        rl_transfer_bus.setOnClickListener(HomeActivity.this);
        rl_station_bus = (RelativeLayout)findViewById(R.id.rl_station_bus);
        rl_station_bus.setOnClickListener(HomeActivity.this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rl_surround_bus:
                intent = new Intent(HomeActivity.this,SurroundBusActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_line_bus:
                intent = new Intent(HomeActivity.this,BusLineQueryActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_station_bus:
                intent = new Intent(HomeActivity.this,BusStationQueryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
