package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.RouteSearch;
import com.yurc.customerbus.R;

/**
 * Date：4/15/2016
 * Author：yurc
 * Describe：换乘查询界面
 */
public class BusTransferQueryActivity extends BaseActivity implements View.OnClickListener{

    public static final String START_LOCATION  = "START_LOCATION";
    public static final String ENDLOCATION  = "END_LOCATION";
    private String startLocation = "";
    private String endLocation;
    private TextView tv_start_location;
    private TextView tv_end_location;
    private ImageView iv_exchange;
    private ImageView iv_search;
    private int busMode = RouteSearch.BusDefault;// 公交默认模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_bus_query);
        initViews();
    }


    public void initViews(){
        tv_start_location = (TextView)findViewById(R.id.tv_start_location);
        tv_end_location = (TextView)findViewById(R.id.tv_end_location);

        iv_exchange = (ImageView)findViewById(R.id.iv_exchange);
        iv_search = (ImageView)findViewById(R.id.iv_search);


        tv_start_location.setOnClickListener(BusTransferQueryActivity.this);
        tv_end_location.setOnClickListener(BusTransferQueryActivity.this);
        iv_exchange.setOnClickListener(BusTransferQueryActivity.this);
        iv_search.setOnClickListener(BusTransferQueryActivity.this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_start_location:
                //TODO 跳至起始地址
                intent = new Intent(BusTransferQueryActivity.this,SearchLocationActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.tv_end_location:
                //TODO 跳至终点地址
                intent = new Intent(BusTransferQueryActivity.this,SearchLocationActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.iv_exchange:
                String temp = startLocation;
                startLocation = endLocation;
                endLocation = temp;
                initStartEndLocation();
                break;
            case R.id.iv_search:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null){
            startLocation = data.getStringExtra("LOCATION");
            if(startLocation == null){
                startLocation = "";
            }else{
                tv_start_location.setText(startLocation);
            }
        }else if(requestCode == 2 && data != null){
            endLocation = data.getStringExtra("LOCATION");
            if(endLocation == null){
                endLocation = "";
            }else{
                tv_end_location.setText(endLocation);
            }
        }
    }
    public void initStartEndLocation(){
        tv_start_location.setText(startLocation);
        tv_end_location.setText(endLocation);
    }

}
