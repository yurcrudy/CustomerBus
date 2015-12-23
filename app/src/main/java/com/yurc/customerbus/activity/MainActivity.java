package com.yurc.customerbus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yurc.customerbus.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestActivity_.intent(this).start();
    }
}
