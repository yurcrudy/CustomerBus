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
    public static ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        context = this;
        initActionBar();
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

    public void initActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_default);//自定义默认ActionBar
        View view = actionBar.getCustomView();
        TextView textView = (TextView)view.findViewById(R.id.tv_title);
        if(!StringUtil.isBlank(actionBarTitle)){
            textView.setText(actionBarTitle);
        }else{
            textView.setText(this.getClass().getName());
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUitl.ToastForShort(context,"title text");
            }
        });
    }

    public void setActionBarTitle(String actionBarTitle) {
        this.actionBarTitle = actionBarTitle;
    }
}
