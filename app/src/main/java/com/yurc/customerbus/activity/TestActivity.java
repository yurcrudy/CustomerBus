package com.yurc.customerbus.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.yurc.customerbus.R;
import com.yurc.customerbus.handler.LocationHandler;
import com.yurc.customerbus.impl.LocationImpl;
import com.yurc.customerbus.util.LocationClient;
import com.yurc.customerbus.util.ToastUitl;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Date：12/23/2015
 * Author：yurc
 * Describe：
 */
@EActivity(R.layout.activity_test)
public class TestActivity extends BaseActivity implements LocationImpl{

    private LocationHandler locationHandler;//定位一个类就足矣
    private AMapLocation amapLocation;

    @ViewById
    Button btn_stop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init(){
        ToastUitl.ToastForShort(TestActivity.this, "TestActivity.init + " + Thread.currentThread().getId());
        locationHandler = new LocationHandler(TestActivity.this,TestActivity.this);
        locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);//启动定位
    }

    @Click(R.id.btn_stop)
    void click(){
        locationHandler.sendEmptyMessage(LocationHandler.START_FLAG);
    }

    //开始定位
    @Override
    public void startLocation() {
        ToastUitl.ToastForShort(TestActivity.this, "startLocation");
        showDialog("正在定位");
    }

    //停止
    @Override
    public void stopLocation() {
        ToastUitl.ToastForShort(TestActivity.this, "stopLocation");
        dismissDialog();
    }

    //完成
    @Override
    public void finishLocation(AMapLocation amapLocation) {
        ToastUitl.ToastForShort(TestActivity.this, "successLocation");
        dismissDialog();
    }

}
