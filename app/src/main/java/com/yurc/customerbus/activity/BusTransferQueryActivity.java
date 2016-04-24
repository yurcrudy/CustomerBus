package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yurc.customerbus.R;
import com.yurc.customerbus.model.Location;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.ToastUtil;

/**
 * Date：4/15/2016
 * Author：yurc
 * Describe：换乘查询界面
 */
public class BusTransferQueryActivity extends BaseActivity implements View.OnClickListener, RouteSearch.OnRouteSearchListener {

    public static final String START_LOCATION  = "START_LOCATION";
    public static final String ENDLOCATION  = "END_LOCATION";
    private String startLocationName = "";
    private String endLocationName = "";
    private TextView tv_start_location;
    private TextView tv_end_location;
    private ImageView iv_exchange;
    private ImageView iv_search;
    private int busMode = RouteSearch.BusDefault;// 公交默认模式
    private LatLonPoint startPoint = null;
    private LatLonPoint endPoint = null;
    private RouteSearch routeSearch;
    private BusRouteResult busRouteResult;

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

        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
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
                String temp = startLocationName;
                startLocationName = endLocationName;
                endLocationName = temp;
                initStartEndLocation();
                break;
            case R.id.iv_search:
                if(startPoint != null && endPoint != null){
                    searchRouteResult(startPoint,endPoint);
                }else{
                    ToastUtil.showForShort(BusTransferQueryActivity.this,R.string.end_location_empty_hint);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null){
            startLocationName = data.getStringExtra("LOCATION");
            Location location = (Location)data.getSerializableExtra("LOCATION_DETAIL");
            startPoint = new LatLonPoint(location.getLatitude(),location.getLongitude());
            if(startLocationName == null){
                startLocationName = "";
            }else{
                tv_start_location.setText(startLocationName);
            }
        }else if(requestCode == 2 && data != null){
            endLocationName = data.getStringExtra("LOCATION");
            Location location = (Location)data.getSerializableExtra("LOCATION_DETAIL");
            endPoint = new LatLonPoint(location.getLatitude(),location.getLongitude());
            if(endLocationName == null){
                endLocationName = "";
            }else{
                tv_end_location.setText(endLocationName);
            }
        }
    }
    public void initStartEndLocation(){
        tv_start_location.setText(startLocationName);
        tv_end_location.setText(endLocationName);
    }



    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
        showDialog("正在查询公交换乘方案...");
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, busMode, "北京", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
        routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        dismissDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);

            } else {
                ToastUtil.showForShort(BusTransferQueryActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.showForShort(BusTransferQueryActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.showForShort(BusTransferQueryActivity.this, R.string.error_key);
        } else {
            ToastUtil.showForShort(BusTransferQueryActivity.this, getString(R.string.error_other)
                    + rCode);
        }

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }
}