package com.yurc.customerbus.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Date：1/13/2016
 * Author：yurc
 * Describe：Toast Common Util
 */
public class ToastUitl {

    public static void ToastForShort(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    public static void ToastForLong(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }

}
