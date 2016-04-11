package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.dao.BusLine;
import com.yurc.customerbus.util.StringUtil;

import java.util.List;

/**
 * Date：2016/4/3
 * Author：yurc
 * Describe：
 */
public class BusLineListAdapter extends BaseAdapter {
    private List<BusLine> list;
    private LayoutInflater inflater;
    private Context context;
    private int itemLayout;

    public BusLineListAdapter(List<BusLine> list, Context context, int itemLayout) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.itemLayout = itemLayout;
    }

    static class ViewHolder{
        TextView tv_line_name;
        TextView tv_start_time;
        TextView tv_end_time;
        TextView tv_direction;
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
        BusLine bus = list.get(position);
        ViewHolder holder = null;
        if(null == convertView){
            convertView = inflater.inflate(itemLayout,null);
            holder = new ViewHolder();
            holder.tv_direction = (TextView)convertView.findViewById(R.id.tv_direction);
            holder.tv_distance = (TextView)convertView.findViewById(R.id.tv_distance);
            holder.tv_end_time = (TextView)convertView.findViewById(R.id.tv_end_time);
            holder.tv_start_time = (TextView)convertView.findViewById(R.id.tv_start_time);
            holder.tv_line_name = (TextView)convertView.findViewById(R.id.tv_line_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tv_line_name.setText(bus.getLinename() + "路");
        holder.tv_start_time.setText("首:" + bus.getStartTime());
        holder.tv_end_time.setText("末:" + bus.getEndTime());
        holder.tv_direction.setText("开往  " + bus.getDirectionStie() + "  方向");
        holder.tv_distance.setText(bus.getDirectionStie() + " | 约" + bus.getDistance() + "米");
        return convertView;
    }
}
