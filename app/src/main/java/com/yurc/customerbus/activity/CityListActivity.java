package com.yurc.customerbus.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.CityListAdapter;
import com.yurc.customerbus.handler.LocationHandler;
import com.yurc.customerbus.impl.LocationImpl;
import com.yurc.customerbus.model.City;
import com.yurc.customerbus.util.ChineseCharToEn;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;


import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Date：1/15/2016
 * Author：yurc
 * Describe：城市列表页面
 */
public class CityListActivity extends BaseActivity implements View.OnClickListener,LocationImpl,DistrictSearch.OnDistrictSearchListener{
    LinearLayout layoutIndex;
    private String selectedCityName = "";
    private boolean judgeCityChangeSuccessful = false;
    /** 字母索引表 */
    private String[] str_index = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q","R","S","T"
            ,"U", "V", "W", "X", "Y", "Z" };// "#",

    private int height;// 字体高度
    private List<City> listData;
    private ListView listView;
    private CityListAdapter adapter;
    private TextView tv_show;// 中间显示标题的文本
    private TextView tv_location;//位置城市
    private ImageView iv_back;
    private ImageView iv_location;
    private LocationHandler locationHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
        layoutIndex.setBackgroundColor(Color.parseColor("#00ffffff"));

        getData();

        tv_show = (TextView) findViewById(R.id.tv);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(CityListActivity.this);

        iv_location = (ImageView) findViewById(R.id.iv_location);
        iv_location.setOnClickListener(CityListActivity.this);


        tv_location = (TextView) findViewById(R.id.tv_location);
        String cityname = SharedPerferenceUtil.getString(CityListActivity.this, DictionaryUtil.CITY_NAME, "");
        if(!cityname.equals("")){
            tv_location.setOnClickListener(CityListActivity.this);
        }
        tv_location.setText(cityname);

        tv_show.setVisibility(View.INVISIBLE);

        locationHandler = new LocationHandler(CityListActivity.this,CityListActivity.this);
    }

    /**
     * 获取城市列表
     */
    public void getData() {
        listData =new ArrayList<City>();
        String[] cityArray = getResources().getStringArray(R.array.city);
        if(cityArray != null){
            for(String cityName : cityArray){
                City city = new City();
                city.setName(cityName);
                listData.add(city);
            }
        }
        //获取首字母
        for (City cityListItem : listData) {
            cityListItem.setIndex(String.valueOf(ChineseCharToEn.getAllFirstLetter(cityListItem.getName()).charAt(0)));
        }
        //排序
        LetterComparator lc = new LetterComparator();
        Collections.sort(listData, lc);

        listView = (ListView) findViewById(R.id.lv_city);
        adapter = new CityListAdapter(CityListActivity.this, listData, str_index);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCityName = listData.get(i).getName();
                getCityDetail(listData.get(i).getName());
            }
        });

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_location:
                setResult(DictionaryUtil.FINISH_CITY_LIST);
                finish();
                break;

            case R.id.iv_location:
                locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);
                break;
        }
    }

    @Override
    public void startLocation() {
        showDialog("正在定位");
    }

    @Override
    public void stopLocation() {
        dismissDialog();
    }

    @Override
    public void finishLocation(AMapLocation amapLocation) {
        if(amapLocation != null){
            amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.LATITUDE, String.valueOf(amapLocation.getLatitude()));
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.LONGITUDE, String.valueOf(amapLocation.getLongitude()));
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.LOCATION_TIME, String.valueOf(amapLocation.getTime()));
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.ADDRESS_DETAIL, amapLocation.getAddress());
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.PROVINCE_NAME, amapLocation.getProvince());

            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.AD_CODE, amapLocation.getAdCode());
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.CITY_CODE, amapLocation.getCityCode());
            SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.CITY_NAME, amapLocation.getCity());

//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = new Date(amapLocation.getTime());
//            df.format(date);//定位时间
//            amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//            amapLocation.getCountry();//国家信息
//            amapLocation.getProvince();//省信息
//            amapLocation.getCity();//城市信息
//            amapLocation.getDistrict();//城区信息
//            amapLocation.getStreet();//街道信息
//            amapLocation.getStreetNum();//街道门牌号信息
//            amapLocation.getCityCode();//城市编码
//            amapLocation.getAdCode();//地区编码
            tv_location.setText(amapLocation.getCity());
            tv_location.setOnClickListener(CityListActivity.this);
        }else{
            LogUtil.v("定位失败.....");
        }
        dismissDialog();
    }

    public void getCityDetail(String str){
        showDialog("正在获取城市编码.....");
        DistrictSearch search = new DistrictSearch(CityListActivity.this);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(str);//传入关键字
        query.setKeywordsLevel(DistrictSearchQuery.KEYWORDS_CITY);
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);
        search.setOnDistrictSearchListener(this);//绑定监听器
        search.searchDistrictAnsy();//开始搜索
    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        if(districtResult != null && districtResult.getDistrict() != null && districtResult.getDistrict().size() > 0){
            boolean judge = false;
            for(DistrictItem item : districtResult.getDistrict()){
                if(item.getName().equals(selectedCityName)){
                    SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.AD_CODE, item.getAdcode());
                    SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.CITY_CODE, item.getCitycode());
                    SharedPerferenceUtil.putString(CityListActivity.this, DictionaryUtil.CITY_NAME, item.getName());
                    judge = true;
                    break;
                }
            }
            if(!judge){
                judgeCityChangeSuccessful = false;
                ToastUtil.showForShort(CityListActivity.this,"获取城市编码失败，请选择其他城市....");
            }else{
                judgeCityChangeSuccessful = true;
                setResult(DictionaryUtil.FINISH_CITY_LIST);
                finish();
            }
        }
        dismissDialog();
    }
}
