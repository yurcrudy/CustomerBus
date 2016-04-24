package com.yurc.customerbus.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Date：1/13/2016
 * Author：yurc
 * Describe：Toast Common Util
 */
public class ToastUtil {

    public static void showForShort(Context context, String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    public static void showForLong(Context context, String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }

    public static void showForLong(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void showForShort(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

}
