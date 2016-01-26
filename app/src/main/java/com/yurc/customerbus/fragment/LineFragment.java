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
 * Describe：线路查询页面
 */
public class LineFragment extends BaseFragment{
    private View view = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_line,container);
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if(viewGroup != null){
            viewGroup.removeView(view);
        }
        return view;
    }
}
