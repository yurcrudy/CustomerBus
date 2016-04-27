package com.yurc.customerbus.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Date：1/18/2016
 * Author：yurc
 * Describe：SharedPerference 工具类
 */
public class SharedPerferenceUtil {
    public static String PREFERENCE_NAME = "connVersion.pref";

    private SharedPerferenceUtil(){
        throw new AssertionError();
    }

    public static boolean putString(Context context,String preferenceName,String key,String value){
        SharedPreferences settings = context.getSharedPreferences(preferenceName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean putString(Context context, String key, String value) {
        return putString(context, PREFERENCE_NAME, key, value);
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        return getString(context, PREFERENCE_NAME, key, defaultValue);
    }

    public static String getString(Context context, String preferenceName,
                                   String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }


    /**
     * 保存布尔值到文件
     * @Title putBoolean
     * @Description 保存布尔值到文件
     * @param context
     * @param key
     * @param value
     * @return boolean
     * @author yjq
     * @date 2015-6-8 下午5:22:41
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        return putBoolean(context, PREFERENCE_NAME, key, value);
    }
    /**
     *
     * @Title putBoolean
     * @Description 保存布尔值到名为preferenceName的文件中
     * @param context
     * @param preferenceName
     * @param key
     * @param value
     * @return boolean
     */
    public static boolean putBoolean(Context context,String preferenceName, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 获取布尔值从文件中 默认值false
     * @Title getBoolean
     * @Description 获取布尔值从文件中 默认值false
     * @param context
     * @param key
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * 获取布尔值从文件中
     * @Title getBoolean
     * @Description 获取布尔值从文件中
     * @param context
     * @param key
     * @param defaultValue
     * @return boolean
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        return getBoolean(context, PREFERENCE_NAME, key, defaultValue);
    }
    /**
     * 获取布尔值从名为preferenceName的文件中
     * @Title getBoolean
     * @Description 获取布尔值从名为preferenceName的文件中
     * @param context
     * @param preferenceName
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context,String preferenceName, String key,
                                     boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }



    public static boolean remove(Context context,String key) {
        return remove(context, PREFERENCE_NAME, key);
    }

    public static boolean remove(Context context, String preferenceName,
                                 String key) {
        SharedPreferences settings = context.getSharedPreferences(
                preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }



}
