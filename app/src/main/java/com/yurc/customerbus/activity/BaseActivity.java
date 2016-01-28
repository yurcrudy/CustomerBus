package com.yurc.customerbus.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yurc.customerbus.R;
import com.yurc.customerbus.util.StringUtil;
import com.yurc.customerbus.util.ToastUitl;
import com.yurc.customerbus.view.LoadingDialog;

/**
 * Date：12/23/2015
 * Author：yurc
 * Describe：all Activity extends this Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context context;
    public String actionBarTitle;
    public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        context = this;
    }

    public void showDialog(String str){
        loadingDialog.setMessage(str);
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
        loadingDialog.show();
    }

    public void dismissDialog(){
        if(loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    /**
     * 初始化默认的actionbar
     * */
    public void initActionBar(){
        actionBarTitle = "BaseActivity";
        TextView textView = (TextView)findViewById(R.id.tv_title);
        textView.setText(actionBarTitle);
    }

    public void setActionBarTitle(String actionBarTitle) {
        this.actionBarTitle = actionBarTitle;
    }
}
