package com.yurc.customerbus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.activity.BaseActivity;
import com.yurc.customerbus.model.City;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date：1/15/2016
 * Author：yurc
 * Describe：城市列表适配器
 */
public class CityListAdapter extends BaseAdapter{
    private Context context;
    private ViewHolder holder;
    List<City> list;
    Map<String, Integer> selector;// 键值是索引表的字母，值为对应在listview中的位置
    String index[];//字母表

    public CityListAdapter(Context context, List<City> list, String index[]){
        this.context = context;
        this.list = list;
        this.index = index;
        selector = new HashMap<String, Integer>();
        // 循环字母表，找出list中对应字母的位置
        for (int j = 0; j < index.length; j++) {
            for (int i = 0; i < list.size(); i++) {
                // 由于已经按照字母排序过了，匹配中第一个就找下一个下标了。
                if (list.get(i).getIndex().equals(index[j].toLowerCase())) {
                    selector.put(index[j], i);
                    break;
                }
            }
        }
    };

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_city, null);
                holder.tv1 = (TextView) convertView.findViewById(R.id.tv1);
                holder.index = (TextView) convertView.findViewById(R.id.tv_index);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // 绑定数据
            City item = list.get(position);
            holder.tv1.setText(item.getName());
            // 显示index
            String currentStr = item.getIndex();
            // 上一项的index
            String previewStr = (position - 1) >= 0 ? list.get(position - 1).getIndex() : " ";
            //判断是否上一次的存在
            if (!previewStr.equals(currentStr)) {
                holder.index.setVisibility(View.VISIBLE);
                holder.index.setText(currentStr);// 文本显示当前滑动的字母
            } else {
                holder.index.setVisibility(View.GONE);
            }
        } catch (OutOfMemoryError e) {
            Runtime.getRuntime().gc();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv1;
        TextView index;//索引字母
    }

    public Map<String, Integer> getSelector() {
        return selector;
    }

    public void setSelector(Map<String, Integer> selector) {
        this.selector = selector;
    }

    public String[] getIndex() {
        return index;
    }

    public void setIndex(String[] index) {
        this.index = index;
    }
}
