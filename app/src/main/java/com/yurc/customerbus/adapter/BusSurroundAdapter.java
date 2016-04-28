package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.dao.BusLine;
import com.yurc.customerbus.model.BusSurround;
import com.yurc.customerbus.util.StringUtil;

import java.util.List;

/**
 * Date：2016/4/3
 * Author：yurc
 * Describe：
 */
public class BusSurroundAdapter extends BaseAdapter {
    private List<BusSurround> list;
    private LayoutInflater inflater;
    private Context context;
    private int itemLayout;

    public BusSurroundAdapter(List<BusSurround> list, Context context, int itemLayout) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.itemLayout = itemLayout;
    }

    static class ViewHolder{
        TextView tv_station_name;
        TextView tv_pass_bus_line;
        TextView tv_distance;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BusSurround bus = list.get(position);
        ViewHolder holder = null;
        if(null == convertView){
            convertView = inflater.inflate(itemLayout,null);
            holder = new ViewHolder();
            holder.tv_station_name = (TextView)convertView.findViewById(R.id.tv_station_name);
            holder.tv_distance = (TextView)convertView.findViewById(R.id.tv_distance);
            holder.tv_pass_bus_line = (TextView)convertView.findViewById(R.id.tv_pass_bus_line);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_station_name.setText(bus.getBusStationDetail().getBusStationName());
        holder.tv_pass_bus_line.setText(bus.getBusStationDetail().getSnippet());
        holder.tv_distance.setText("距离约" + bus.getDistance()+ "米");
        return convertView;
    }
}
