package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.model.BusTransferBusItem;
import com.yurc.customerbus.model.BusTransferDetail;
import com.yurc.customerbus.model.BusTransferStep;

import java.util.List;

/**
 * Date：4/25/2016
 * Author：yurc
 * Describe：公交换乘方案适配器
 */
public class BusTransferAdapter extends BaseAdapter{
    private Context context;
    private int itemLayout;
    private LayoutInflater inflater;
    private List<BusTransferDetail> list;
    private int minWalkTimes = 0;//走路耗时最少
    private int fastest= 0;//最快
    private int minTransfer = 0;//换乘最少

    public BusTransferAdapter(Context context, int itemLayout, List<BusTransferDetail> list) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.inflater = LayoutInflater.from(this.context);
        this.list = list;
        for(int i = 1;i < list.size();i++){
            if(list.get(i).getDuration() < list.get(fastest).getDuration()){
                this.fastest = i;
            }
            if(list.get(i).getTransferNum() < list.get(minTransfer).getTransferNum()){
                this.minTransfer = i;
            }
            if(list.get(i).getWalkDistance() < list.get(minWalkTimes).getWalkDistance()){
                this.minWalkTimes = i;
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        TextView tv_bus_lines;
        TextView tv_detail;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(null == view){
            holder = new ViewHolder();
            view = inflater.inflate(itemLayout,null);
            holder.tv_bus_lines = (TextView)view.findViewById(R.id.tv_bus_lines);
            holder.tv_detail = (TextView)view.findViewById(R.id.tv_detail);
            view.setTag(holder);

        }else{
            holder = (ViewHolder)view.getTag();
        }
        String busLines = "";
        BusTransferDetail busTransferDetail = list.get(i);
        for(BusTransferStep busTransferStep : busTransferDetail.getBusTransferStepList()){
            for(BusTransferBusItem busTransferBusItem : busTransferStep.getBusTransferBusItems()){
                busLines += busTransferBusItem.getBusLineName().substring(0,busTransferBusItem.getBusLineName().indexOf("(")) + "->";
            }
        }
        busLines = busLines.substring(0,busLines.length() - 2);
        holder.tv_bus_lines.setText(busLines);
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
        holder.tv_detail.setText(detail);
        return view;
    }
}
