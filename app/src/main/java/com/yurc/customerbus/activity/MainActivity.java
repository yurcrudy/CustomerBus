package com.yurc.customerbus.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.fragment.LineFragment;
import com.yurc.customerbus.fragment.MineFragment;
import com.yurc.customerbus.fragment.StartEndFragment;
import com.yurc.customerbus.fragment.StationFragment;
import com.yurc.customerbus.util.LogUtil;

public class MainActivity extends AppCompatActivity {

    private final static int START_END_TAB = 1;
    private final static int LINE_TAB = 2;
    private final static int STATION_TAB = 3;
    private final static int MINE_TAB = 4;
    private LineFragment lineFragment = null;//线路查询
    private MineFragment mineFragment = null;//我的页面
    private StartEndFragment startEndFragment = null;//起点终点查询
    private StationFragment stationFragment = null;//站点查询

    private FrameLayout mLineFrameLayout = null;//线路
    private FrameLayout mStartEndFrameLayout = null;//起点终点
    private FrameLayout mStationFrameLayout = null;//站点搜索
    private FrameLayout mMineFrameLayout = null;//我的

    private ImageView mLineImageView = null;//线路图标选择按钮
    private ImageView mStartEndImageView = null;//起点终点站
    private ImageView mStationImageView = null;//站点搜索选择图片
    private ImageView mMineImageView = null;//我的页面选择图片空间

    private View.OnClickListener onClickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化所有的fragment
        if(savedInstanceState == null){
            startEndFragment = (StartEndFragment) Fragment.instantiate(this, StartEndFragment.class.getName());
        }else{
            startEndFragment = (StartEndFragment) getSupportFragmentManager().getFragment(savedInstanceState, StartEndFragment.class.getName());
        }
        if(savedInstanceState == null){
            lineFragment = (LineFragment) Fragment.instantiate(this, LineFragment.class.getName());
        }else{
            lineFragment = (LineFragment) getSupportFragmentManager().getFragment(savedInstanceState, LineFragment.class.getName());
        }
        if(savedInstanceState == null){
            stationFragment = (StationFragment) Fragment.instantiate(this, StationFragment.class.getName());
        }else{
            stationFragment = (StationFragment) getSupportFragmentManager().getFragment(savedInstanceState, StationFragment.class.getName());
        }
        if(savedInstanceState == null){
            mineFragment = (MineFragment) Fragment.instantiate(this, MineFragment.class.getName());
        }else{
            mineFragment = (MineFragment) getSupportFragmentManager().getFragment(savedInstanceState, MineFragment.class.getName());
        }
        setContentView(R.layout.activity_main);
        initViews();

    }
    /**
     * 初始化控件
     * */
    public void initViews(){
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(view.getId());
            }
        };
        mStartEndFrameLayout = (FrameLayout)findViewById(R.id.fl_start_end);
        mLineFrameLayout = (FrameLayout)findViewById(R.id.fl_line);
        mStationFrameLayout = (FrameLayout)findViewById(R.id.fl_station);
        mMineFrameLayout = (FrameLayout)findViewById(R.id.fl_mine);

        mStartEndImageView = (ImageView)findViewById(R.id.iv_start_end);
        mLineImageView = (ImageView)findViewById(R.id.iv_line);
        mStationImageView = (ImageView)findViewById(R.id.iv_station);
        mMineImageView = (ImageView)findViewById(R.id.iv_mine);

        mStartEndImageView.setOnClickListener(onClickListener);
        mLineImageView.setOnClickListener(onClickListener);
        mStationImageView.setOnClickListener(onClickListener);
        mMineImageView.setOnClickListener(onClickListener);

//        chooseTab(START_END_TAB);
    }
    /**
     * 底栏点击事件
     * */
    public void click(int id){
        LogUtil.v("click");
        switch (id){
            case R.id.iv_start_end:
                chooseTab(START_END_TAB);
                break;
            case R.id.iv_line:
                chooseTab(LINE_TAB);
                break;
            case R.id.iv_station:
                chooseTab(STATION_TAB);
                break;
            case R.id.iv_mine:
                chooseTab(MINE_TAB);
                break;
        }
    }
    /**
     * 选择不同标签
     * */
    public void chooseTab(int type){
        LogUtil.v("chooseTab type");
        switch (type){
            case START_END_TAB:
                changeSelect(START_END_TAB);
                break;
            case LINE_TAB:
                changeSelect(LINE_TAB);
                break;
            case STATION_TAB:
                changeSelect(STATION_TAB);
                break;
            case MINE_TAB:
                changeSelect(MINE_TAB);
                break;
        }
    }

    /**
     * 切换页面
     * */
    public void chooseTab(Fragment fragment){
        LogUtil.v("chooseTab Fragment");
        if(!fragment.isAdded()){
            LogUtil.v("chooseTab isAdded");
            //得到Fragment事务管理器
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            LogUtil.v("chooseTab fragmentTransaction");
            //替换当前的页面
            fragmentTransaction.add(R.id.frame_content, fragment);
            //事务管理提交
            LogUtil.v("chooseTab replace");
            fragmentTransaction.commit();
            LogUtil.v("chooseTab commit");
        }
    }

    public void clearAllChoose(){
        LogUtil.v("clearAllChoose");
        mStartEndFrameLayout.setSelected(false);
        mStartEndImageView.setSelected(false);

        mLineFrameLayout.setSelected(false);
        mLineImageView.setSelected(false);

        mStationFrameLayout.setSelected(false);
        mStationImageView.setSelected(false);

        mMineFrameLayout.setSelected(false);
        mMineImageView.setSelected(false);
    }

    private void changeSelect(int type) {
        LogUtil.v("changeSelect");
        clearAllChoose();
        switch (type) {
            case START_END_TAB://起点终点
                mStartEndFrameLayout.setSelected(true);
                mStartEndImageView.setSelected(true);
                if (startEndFragment == null)
                    startEndFragment = new StartEndFragment();
//                    startEndFragment = (StartEndFragment)Fragment.instantiate(MainActivity.this,StartEndFragment.class.getName());
                chooseTab(startEndFragment);
                break;
            case LINE_TAB://线路搜索
                mLineFrameLayout.setSelected(true);
                mLineImageView.setSelected(true);
                if (lineFragment == null)
                    lineFragment = new LineFragment();
//                    lineFragment = (LineFragment)Fragment.instantiate(MainActivity.this,LineFragment.class.getName());
                chooseTab(lineFragment);
                break;
            case STATION_TAB://站点搜索
                mStationFrameLayout.setSelected(true);
                mStationImageView.setSelected(true);
                if (stationFragment == null)
                    stationFragment = new StationFragment();
//                    stationFragment = (StationFragment)Fragment.instantiate(MainActivity.this,StationFragment.class.getName());
                chooseTab(stationFragment);
                break;
            case MINE_TAB://我的
                mMineFrameLayout.setSelected(true);
                mMineImageView.setSelected(true);
                if (mineFragment == null)
                    mineFragment = new MineFragment();
//                    mineFragment = (MineFragment)Fragment.instantiate(MainActivity.this,MineFragment.class.getName());
                chooseTab(mineFragment);
                break;
        }
    }

}
