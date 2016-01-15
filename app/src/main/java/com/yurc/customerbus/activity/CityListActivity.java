package com.yurc.customerbus.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.CityListAdapter;
import com.yurc.customerbus.model.City;
import com.yurc.customerbus.util.ChineseCharToEn;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Date：1/15/2016
 * Author：yurc
 * Describe：城市列表页面
 */
public class CityListActivity extends BaseActivity{
    LinearLayout layoutIndex;
    /** 字母索引表 */
    private String[] str_index = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q","R","S","T"
            ,"U", "V", "W", "X", "Y", "Z" };// "#",

    private int height;// 字体高度
    private List<City> listData;
    private ListView listView;
    private CityListAdapter adapter;
    private TextView tv_show;// 中间显示标题的文本

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("城市列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_city_list);
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
        layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));

        getData();

        tv_show = (TextView) findViewById(R.id.tv);
        tv_show.setVisibility(View.INVISIBLE);

    }

    /**
     * 获取城市列表
     */
    public void getData() {
        City ci1=new City();
        ci1.setName("北京");
        City ci2=new City();
        ci2.setName("上海");
        City ci3=new City();
        ci3.setName("广州");
        City ci4=new City();
        ci4.setName("广西");
        City ci5=new City();
        ci5.setName("长沙");
        City ci6=new City();
        ci6.setName("贵阳");
        City ci7=new City();
        ci7.setName("福建");

        ArrayList<City> list=new ArrayList<City>();
        list.add(ci1);
        list.add(ci1);
        list.add(ci1);
        list.add(ci1);
        list.add(ci1);
        list.add(ci1);
        list.add(ci2);list.add(ci2);list.add(ci2);list.add(ci2);list.add(ci2);list.add(ci2);
        list.add(ci3);list.add(ci3);list.add(ci3);list.add(ci3);list.add(ci3);
        list.add(ci4);list.add(ci4);list.add(ci4);list.add(ci4);list.add(ci4);
        list.add(ci5);    list.add(ci5);    list.add(ci5);    list.add(ci5);
        list.add(ci6);list.add(ci6);list.add(ci6);list.add(ci6);
        list.add(ci7);list.add(ci7);list.add(ci7);list.add(ci7);

        //获取首字母
        for (City cityListItem : list) {
            cityListItem.setIndex(String.valueOf(ChineseCharToEn.getAllFirstLetter(cityListItem.getName()).charAt(0)));
        }
        //排序
        LetterComparator lc = new LetterComparator();
        Collections.sort(list, lc);

        listView = (ListView) findViewById(R.id.lv_city);
        adapter = new CityListAdapter(CityListActivity.this, list, str_index);
        listView.setAdapter(adapter);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // 在oncreate里面执行下面的代码没反应，因为oncreate里面得到的getHeight=0
        // System.out.println("layoutIndex.getHeight()=" +
        // layoutIndex.getHeight());
        height = layoutIndex.getHeight() / str_index.length;
        getIndexView();
    }

    /** 绘制索引列表 */
    public void getIndexView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, height);
        // params.setMargins(10, 5, 10, 0);
        for (int i = 0; i < str_index.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(str_index[i]);
            tv.setPadding(10, 0, 10, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < str_index.length) {// 防止越界
                        String key = str_index[index];
                        if (adapter.getSelector().containsKey(key)) {
                            // 获得位置
                            int pos = adapter.getSelector().get(key);
                            if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                listView.setSelectionFromTop(pos + listView.getHeaderViewsCount(), 0);
                            } else {
                                listView.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(str_index[index]);
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            //按下颜色
                            layoutIndex.setBackgroundColor(Color.parseColor("#aaffffff"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            //释放还原
                            layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));
                            tv_show.setVisibility(View.INVISIBLE);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private class LetterComparator implements Comparator<City> {

        @Override
        public int compare(City lhs, City rhs) {
            return Collator.getInstance().compare(lhs.getIndex(), rhs.getIndex());
        }
    }
}
