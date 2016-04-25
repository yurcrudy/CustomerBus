package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.model.BusTransferBusItem;
import com.yurc.customerbus.model.BusTransferDetail;
import com.yurc.customerbus.model.BusTransferStep;

/**
 * Date：2016/4/25
 * Author：yurc
 * Describe：公交换乘方案详情页面
 */
public class BusTransferDetailActivity extends BaseActivity{
    private BusTransferDetail busTransferDetail;
    private TextView tv_bus_lines;
    private TextView tv_detail;
    private ListView lv_transfer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_transfer_detail);
        initViews();
    }
    public void initViews(){
        tv_detail = (TextView)findViewById(R.id.tv_detail);
        tv_bus_lines = (TextView)findViewById(R.id.tv_bus_lines);
        lv_transfer = (ListView)findViewById(R.id.lv_transfer);

        busTransferDetail = (BusTransferDetail)getIntent().getSerializableExtra("BUS_TRANSFER_DETAIL");
        String busLines = "";
        for(BusTransferStep busTransferStep : busTransferDetail.getBusTransferStepList()){
            for(BusTransferBusItem busTransferBusItem : busTransferStep.getBusTransferBusItems()){
                busLines += busTransferBusItem.getBusLineName().substring(0,busTransferBusItem.getBusLineName().indexOf("(")) + "->";
            }
        }
        busLines = busLines.substring(0,busLines.length() - 2);
        tv_bus_lines.setText(busLines);
        String detail = "";
        String walkdistance = String.valueOf(busTransferDetail.getWalkDistance() / 1000);
        if(walkdistance.length() > 3){
            walkdistance = walkdistance.substring(0,4);
        }
        String time = String.valueOf(busTransferDetail.getDuration() / 60);
        if(time.indexOf(".") > 0){
            time = time.substring(0,time.indexOf("."));
        }
        detail += busTransferDetail.getTransferNum() + "次换乘,乘车" +
                busTransferDetail.getPassStationNum() + "站,步行约" +
                walkdistance + "公里,约" +
                time + "分钟";
        tv_detail.setText(detail);
    }
}
