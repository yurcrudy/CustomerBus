package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.model.BusStationDetail;

import java.util.List;

/**
 * Date：4/15/2016
 * Author：yurc
 * Describe：站点查询适配器
 */
public class BusStationQueryAdapter extends BaseAdapter{

    private List<BusStationDetail> list;
    private Context context;
    private LayoutInflater inflater;
    private int itemLayout;

    public BusStationQueryAdapter(List<BusStationDetail> list, Context context, int itemLayout) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.itemLayout = itemLayout;
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
        TextView tv_station_name;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(null == view){
            holder = new ViewHolder();
            view = inflater.inflate(itemLayout,null);
            holder.tv_station_name = (TextView)view.findViewById(R.id.tv_station_name);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        holder.tv_station_name.setText(list.get(i).getBusStationName());
        return view;
    }
}
