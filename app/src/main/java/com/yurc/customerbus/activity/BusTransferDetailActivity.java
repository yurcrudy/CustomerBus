package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusTransferDescriptionAdapter;
import com.yurc.customerbus.model.BusTransferBusItem;
import com.yurc.customerbus.model.BusTransferDescription;
import com.yurc.customerbus.model.BusTransferDetail;
import com.yurc.customerbus.model.BusTransferStep;
import com.yurc.customerbus.model.WalkItemStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/25
 * Author：yurc
 * Describe：公交换乘方案详情页面
 */
public class BusTransferDetailActivity extends BaseActivity implements View.OnClickListener{
    private BusTransferDetail busTransferDetail;
    private TextView tv_bus_lines;
    private TextView tv_detail;
    private ListView lv_transfer;
    private BusTransferDescriptionAdapter busTransferDescriptionAdapter;
    private List<BusTransferDescription> busTransferDescriptionList;
    private ImageView iv_back;
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

        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusTransferDetailActivity.this);
        busTransferDescriptionList = new ArrayList<BusTransferDescription>();

        busTransferDescriptionAdapter = new BusTransferDescriptionAdapter(BusTransferDetailActivity.this,R.layout.list_item_bus_transfer_description,busTransferDescriptionList);
        lv_transfer.setAdapter(busTransferDescriptionAdapter);
        busTransferDetail = (BusTransferDetail)getIntent().getSerializableExtra("BUS_TRANSFER_DETAIL");
        String originLocation = getIntent().getStringExtra("ORIGIN");
        String endLocation = getIntent().getStringExtra("END");
        busTransferDescriptionList.add(new BusTransferDescription(originLocation,"",false,"起"));

        String busLines = "";
        for(BusTransferStep busTransferStep : busTransferDetail.getBusTransferStepList()){
            if(busTransferStep.getWalkItem()!=null && busTransferStep.getWalkItem().getWalkItemStepList().size()!= 0 ){
                String decribe = busTransferStep.getWalkItem().getWalkItemStepList().get(busTransferStep.getWalkItem().getWalkItemStepList().size() - 1).getDescribe();
                if(decribe.contains("到")){
                    decribe = decribe.substring(decribe.indexOf("到"),decribe.length());
                    decribe = "步行约" + busTransferStep.getWalkItem().getDuration()+ "米" + decribe;
                    busTransferDescriptionList.add(new BusTransferDescription(decribe,"",false));
                }else{
                    decribe = "步行约" + busTransferStep.getWalkItem().getDuration() + "米";
                    busTransferDescriptionList.add(new BusTransferDescription(decribe,"",false));
                }
                for(WalkItemStep walkItemStep :busTransferStep.getWalkItem().getWalkItemStepList()){
                    if(walkItemStep.getDistance() > 0){

                    }else{
                        busTransferDescriptionList.add(new BusTransferDescription(walkItemStep.getDescribe(),"",false));
                    }
                }
            }
            for(BusTransferBusItem busTransferBusItem : busTransferStep.getBusTransferBusItems()){
                busTransferDescriptionList.add(new BusTransferDescription(busTransferBusItem.getBusLineName(),
                        "经过" + busTransferBusItem.getPassStaionNum() + "个站，" +
                                busTransferBusItem.getPassStationlist().get(busTransferBusItem.getPassStationlist().size() - 1).getBusStationName() + "站下车",true));
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
        busTransferDescriptionList.add(new BusTransferDescription(endLocation, "", false, "终"));
        busTransferDescriptionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
