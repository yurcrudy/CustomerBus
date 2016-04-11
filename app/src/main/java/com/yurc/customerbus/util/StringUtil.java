package com.yurc.customerbus.util;

/**
 * Date：1/13/2016
 * Author：yurc
 * Describe：String common Util
 */
public class StringUtil {
    private StringUtil(){
        throw new AssertionError();
    }
    /**
     * is null or its length is 0 or it is made by space
     * */
    public static boolean isBlank(String str){
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str){
        return (str == null || str.length() == 0);
    }

    /**
     * int to the String "08:08" | "10:10"
     * */
    public static String IntToTimeStr(int hour,int minute){
        String timeStr = "";
        if(hour >= 10){
            timeStr += hour;
        }else{
            timeStr += "0" + hour;
        }
        timeStr += ":";
        if(minute >= 10){
            timeStr += minute;
        }else{
            timeStr += "0" + minute;
        }
        return timeStr;
    }
}
