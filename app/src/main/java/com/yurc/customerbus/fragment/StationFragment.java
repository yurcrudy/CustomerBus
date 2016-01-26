package com.yurc.customerbus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yurc.customerbus.R;

/**
 * Date：1/26/2016
 * Author：yurc
 * Describe：站点搜索页面
 */
public class StationFragment extends BaseFragment{
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_station,container,false);//必须为false
        }
        return view;
    }
}
