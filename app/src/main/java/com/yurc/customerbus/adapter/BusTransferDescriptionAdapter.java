package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.model.BusTransferDescription;

import java.util.List;

/**
 * Date：4/26/2016
 * Author：yurc
 * Describe：换乘方案步骤
 */
public class BusTransferDescriptionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int itemLayout;
    private List<BusTransferDescription> busTransferDescriptionList;

    public BusTransferDescriptionAdapter(Context context, int itemLayout, List<BusTransferDescription> busTransferDescriptionList) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.itemLayout = itemLayout;
        this.busTransferDescriptionList = busTransferDescriptionList;
    }

    @Override
    public int getCount() {
        return busTransferDescriptionList.size();
    }

    @Override
    public Object getItem(int i) {
        return busTransferDescriptionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder{
        TextView tv_description;
        TextView tv_detail;
        TextView tv_tag;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(itemLayout,null);
            holder.tv_description = (TextView)view.findViewById(R.id.tv_description);
            holder.tv_detail = (TextView)view.findViewById(R.id.tv_detail);
            holder.tv_tag = (TextView)view.findViewById(R.id.tv_tag);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }
        BusTransferDescription busTransferDescription = busTransferDescriptionList.get(i);
        holder.tv_description.setText(busTransferDescription.getDescription());
        if(busTransferDescription.getDetail() != null && !busTransferDescription.getDetail().equals("")){
            holder.tv_detail.setText(busTransferDescription.getDetail());
            holder.tv_detail.setVisibility(View.VISIBLE);
        }else{
            holder.tv_detail.setVisibility(View.GONE);
        }
        if(busTransferDescription.isJudgeBus()){
            holder.tv_tag.setText("车");
        }else{
            holder.tv_tag.setText("步");
        }
        if(busTransferDescription.getStartOrEnd() != null){
            holder.tv_tag.setText(busTransferDescription.getStartOrEnd());
        }
        return view;
    }
}
