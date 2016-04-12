package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.model.BusLineDetail;

import java.util.List;

/**
 * Date：2016/4/12
 * Author：yurc
 * Describe：
 */
public class BusLineQueryAdapter extends BaseAdapter{
    private List<BusLineDetail> list;
    private LayoutInflater inflater;
    private int itemLayout;
    private Context context;

    public BusLineQueryAdapter(List<BusLineDetail> list, int itemLayout, Context context) {
        this.list = list;
        this.itemLayout = itemLayout;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
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
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(itemLayout,null);
            holder = new ViewHolder();
            holder.tv_first_last_station = (TextView)convertView.findViewById(R.id.tv_first_last_station);
            holder.tv_direction = (TextView)convertView.findViewById(R.id.tv_direction);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        BusLineDetail busLineDetail = list.get(position);
        holder.tv_first_last_station.setText(busLineDetail.getBusLineName());
        holder.tv_direction.setText("开往 " + busLineDetail.getTerminalStationName() + " 方向");
        return convertView;
    }

    static class ViewHolder{
        TextView tv_first_last_station;
        TextView tv_direction;
    }


}
