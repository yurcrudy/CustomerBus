package com.yurc.customerbus.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yurc.customerbus.view.LoadingDialog;

/**
 * Date：12/23/2015
 * Author：yurc
 * Describe：all Activity extends this Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    public LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
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



}
