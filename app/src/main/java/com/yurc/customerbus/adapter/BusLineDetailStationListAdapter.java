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
 * Date：2016/4/14
 * Author：yurc
 * Describe：线路详情站点列表详情
 */
public class BusLineDetailStationListAdapter extends BaseAdapter{
    private int itemLayout;
    private List<BusStationDetail> list;
    private Context context;
    private LayoutInflater inflater;

    public BusLineDetailStationListAdapter(int itemLayout, List<BusStationDetail> list, Context context) {
        this.itemLayout = itemLayout;
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = inflater.inflate(itemLayout,null);
            holder = new ViewHolder();
            holder.tv_num = (TextView)convertView.findViewById(R.id.tv_num);
            holder.tv_station_name = (TextView)convertView.findViewById(R.id.tv_station_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_num.setText("" + (position + 1));
        holder.tv_station_name.setText(list.get(position).getBusStationName());
        return convertView;
    }

    static class ViewHolder{
        TextView tv_num;
        TextView tv_station_name;
    }
}
