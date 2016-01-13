package com.yurc.customerbus.application;

import android.app.Application;

import com.yurc.customerbus.util.LogUtil;
import com.yurc.customerbus.util.ToastUitl;

/**
 * Date：1/13/2016
 * Author：yurc
 * Describe：Application Class
 */
public class BusApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.v("application init!");
    }
}
