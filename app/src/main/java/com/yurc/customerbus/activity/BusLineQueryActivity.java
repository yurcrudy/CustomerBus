package com.yurc.customerbus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.overlay.BusLineOverlay;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.amap.api.services.busline.BusStationQuery;
import com.yurc.customerbus.R;
import com.yurc.customerbus.adapter.BusLineQueryAdapter;
import com.yurc.customerbus.model.BusLineDetail;
import com.yurc.customerbus.util.DictionaryUtil;
import com.yurc.customerbus.util.JsonUtil;
import com.yurc.customerbus.util.SharedPerferenceUtil;
import com.yurc.customerbus.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date：2016/4/10
 * Author：yurc
 * Describe：线路查询列表页面
 */
public class BusLineQueryActivity extends BaseActivity implements View.OnClickListener, BusLineSearch.OnBusLineSearchListener,AdapterView.OnItemClickListener{
    private List<BusLineDetail> busLineDetailList;
    private BusLineQueryAdapter busLineQueryAdapter;
    private ListView lv_history;
    private LinearLayout ll_del;
    private EditText et_busline_name;
    private ImageView iv_back;
    private ImageView iv_search;
    private int currentpage;
    private BusLineQuery busLineQuery;// 公交线路查询的查询类
    private String cityCode; //城市代码
    private BusLineSearch busLineSearch;// 公交线路列表查询
    private BusLineResult busLineResult;// 公交线路搜索返回的结果
    private List<BusLineItem> lineItems = null;// 公交线路搜索返回的busline
    private static final String BUSLINE_DETAIL = "BUSLINE_DETAIL";
    private TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busline_query);
        initViews();
    }

    public void initViews(){
        busLineDetailList = new ArrayList<BusLineDetail>();
        busLineQueryAdapter = new BusLineQueryAdapter(busLineDetailList,R.layout.list_item_busline_query,BusLineQueryActivity.this);
        //todo  加载历史数据
        lv_history = (ListView)findViewById(R.id.lv_history);
        lv_history.setAdapter(busLineQueryAdapter);
        ll_del = (LinearLayout)findViewById(R.id.ll_del);
        ll_del.setOnClickListener(BusLineQueryActivity.this);
        et_busline_name = (EditText)findViewById(R.id.et_busline_name);
        lv_history.setOnItemClickListener(BusLineQueryActivity.this);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setOnClickListener(BusLineQueryActivity.this);
        iv_search = (ImageView)findViewById(R.id.iv_search);
        iv_search.setOnClickListener(BusLineQueryActivity.this);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setOnClickListener(BusLineQueryActivity.this);
        tv_title.setFocusable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_del:
                ToastUtil.ToastForLong(BusLineQueryActivity.this, "清空");
                break;
            case R.id.iv_search:
                searchLine();
                break;


        }
    }


    /**
     * 公交线路搜索
     */
    public void searchLine() {
        currentpage = 0;// 第一页默认从0开始
        showDialog("正在搜索....");
        String search = et_busline_name.getText().toString().trim();
        if ("".equals(search)) {
            search = "K3";
            et_busline_name.setText(search);
        }
        cityCode = SharedPerferenceUtil.getString(BusLineQueryActivity.this, DictionaryUtil.CITY_CODE,"440400");

        busLineQuery = new BusLineQuery(search, BusLineQuery.SearchType.BY_LINE_NAME,
                cityCode);// 第一个参数表示公交线路名，第二个参数表示公交线路查询，第三个参数表示所在城市名或者城市区号
        busLineQuery.setPageSize(10);// 设置每页返回多少条数据
        busLineQuery.setPageNumber(currentpage);// 设置查询第几页，第一页从0开始算起
        busLineSearch = new BusLineSearch(this, busLineQuery);// 设置条件
        busLineSearch.setOnBusLineSearchListener(this);// 设置查询结果的监听
        busLineSearch.searchBusLineAsyn();// 异步查询公交线路名称
        // 公交站点搜索事例


    }

    @Override
    public void onBusLineSearched(BusLineResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getQuery() != null
                    && result.getQuery().equals(busLineQuery)) {
                if (result.getQuery().getCategory() == BusLineQuery.SearchType.BY_LINE_NAME) {
                    if (result.getPageCount() > 0
                            && result.getBusLines() != null
                            && result.getBusLines().size() > 0) {
                        busLineResult = result;
                        lineItems = result.getBusLines();
                        showResultList(lineItems);
                    }
                } else if (result.getQuery().getCategory() == BusLineQuery.SearchType.BY_LINE_ID) {
//                    aMap.clear();// 清理地图上的marker
                    busLineResult = result;
                    lineItems = busLineResult.getBusLines();
//                    BusLineOverlay busLineOverlay = new BusLineOverlay(this,
//                            aMap, lineItems.get(0));
//                    busLineOverlay.removeFromMap();
//                    busLineOverlay.addToMap();
//                    busLineOverlay.zoomToSpan();
                }
            } else {
                ToastUtil.ToastForShort(BusLineQueryActivity.this, R.string.no_result);
            }
        } else if (rCode == 27) {
            ToastUtil.ToastForShort(BusLineQueryActivity.this, R.string.error_network);
        } else if (rCode == 32) {
            ToastUtil.ToastForShort(BusLineQueryActivity.this, R.string.error_key);
        } else {
            ToastUtil.ToastForShort(BusLineQueryActivity.this, R.string.error_other);
        }
        dismissDialog();
    }

    public void showResultList(List<BusLineItem> items){
        if(items != null && !items.isEmpty()){
            busLineDetailList.clear();
            ll_del.setVisibility(View.GONE);
            for(BusLineItem item : items){
                busLineDetailList.add(new BusLineDetail(item));
            }
            busLineQueryAdapter.notifyDataSetChanged();
        }
    }
    public void showResultforHistory(List<BusLineDetail> items){
        if(items != null && !items.isEmpty()){
            busLineDetailList.clear();
            busLineDetailList.addAll(items);
        }
        busLineQueryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(BusLineQueryActivity.this,BusLineDetailActivity.class);
        intent.putExtra("BUSLINE_DETAIL",busLineDetailList.get(position));
        startActivity(intent);
    }
}
