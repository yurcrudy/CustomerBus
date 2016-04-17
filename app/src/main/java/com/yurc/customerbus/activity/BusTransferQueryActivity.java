package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yurc.customerbus.R;

/**
 * Date：4/15/2016
 * Author：yurc
 * Describe：换乘查询界面
 */
public class BusTransferQueryActivity extends BaseActivity implements View.OnClickListener{

    public static final String START_LOCATION  = "START_LOCATION";
    public static final String ENDLOCATION  = "END_LOCATION";

    private TextView tv_start_location;
    private TextView tv_end_location;
    private ImageView iv_exchange;
    private ImageView iv_search;

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
                break;
            case R.id.tv_end_location:
                //TODO 跳至终点地址
                intent = new Intent(BusTransferQueryActivity.this,SearchLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_exchange:
                break;
            case R.id.iv_search:
                break;
        }
    }
}
