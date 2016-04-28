package com.yurc.customerbus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.yurc.customerbus.R;
import com.yurc.customerbus.handler.LocationHandler;
import com.yurc.customerbus.impl.LocationImpl;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;

/**
 * Date：3/24/2016
 * Author：yurc
 * Describe：APP 首页
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener,LocationImpl{
    private RelativeLayout rl_surround_bus;
    private RelativeLayout rl_line_bus;
    private RelativeLayout rl_transfer_bus;
    private RelativeLayout rl_station_bus;
    private LinearLayout ll_city;
    private TextView tv_city;
    private LocationHandler locationHandler;//定位控制类

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
        tv_city = (TextView)findViewById(R.id.tv_city);
        rl_surround_bus = (RelativeLayout)findViewById(R.id.rl_surround_bus);
        rl_surround_bus.setOnClickListener(HomeActivity.this);
        rl_line_bus = (RelativeLayout)findViewById(R.id.rl_line_bus);
        rl_line_bus.setOnClickListener(HomeActivity.this);
        rl_transfer_bus = (RelativeLayout)findViewById(R.id.rl_transfer_bus);
        rl_transfer_bus.setOnClickListener(HomeActivity.this);
        rl_station_bus = (RelativeLayout)findViewById(R.id.rl_station_bus);
        rl_station_bus.setOnClickListener(HomeActivity.this);
        ll_city = (LinearLayout)findViewById(R.id.ll_city);
        ll_city.setOnClickListener(HomeActivity.this);
        locationHandler = new LocationHandler(HomeActivity.this,HomeActivity.this);
        tv_city.setText(SharedPerferenceUtil.getString(HomeActivity.this,DictionaryUtil.CITY_NAME,"珠海市"));

//        locationHandler.sendEmptyMessage(1);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rl_surround_bus:
                intent = new Intent(HomeActivity.this,SurroundBusActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.rl_line_bus:
                intent = new Intent(HomeActivity.this,BusLineQueryActivity.class);
                startActivityForResult(intent,3);
                break;
            case R.id.rl_station_bus:
                intent = new Intent(HomeActivity.this,BusStationQueryActivity.class);
                startActivityForResult(intent,4);
                break;
            case R.id.rl_transfer_bus:
                intent = new Intent(HomeActivity.this,BusTransferQueryActivity.class);
                startActivityForResult(intent,5);
                break;
            case R.id.ll_city:
                intent = new Intent(HomeActivity.this,CityListActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tv_city.setText(SharedPerferenceUtil.getString(HomeActivity.this,DictionaryUtil.CITY_NAME,"珠海市"));
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void startLocation() {
        showDialog("正在定位....");
    }

    @Override
    public void stopLocation() {
        dismissDialog();
    }

    @Override
    public void finishLocation(AMapLocation amapLocation) {
        if(amapLocation != null){
            amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.LATITUDE, String.valueOf(amapLocation.getLatitude()));
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.LONGITUDE, String.valueOf(amapLocation.getLongitude()));
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.LOCATION_TIME, String.valueOf(amapLocation.getTime()));
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.ADDRESS_DETAIL, amapLocation.getAddress());
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.PROVINCE_NAME, amapLocation.getProvince());

            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.AD_CODE, amapLocation.getAdCode());
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.CITY_CODE, amapLocation.getCityCode());
            SharedPerferenceUtil.putString(HomeActivity.this, DictionaryUtil.CITY_NAME, amapLocation.getCity());

//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = new Date(amapLocation.getTime());
//            df.format(date);//定位时间
//            amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//            amapLocation.getCountry();//国家信息
//            amapLocation.getProvince();//省信息
//            amapLocation.getCity();//城市信息
//            amapLocation.getDistrict();//城区信息
//            amapLocation.getStreet();//街道信息
//            amapLocation.getStreetNum();//街道门牌号信息
//            amapLocation.getCityCode();//城市编码
//            amapLocation.getAdCode();//地区编码
            tv_city.setText(amapLocation.getCity());
        }else{
            LogUtil.v("定位失败.....");
        }
        dismissDialog();
    }
}
