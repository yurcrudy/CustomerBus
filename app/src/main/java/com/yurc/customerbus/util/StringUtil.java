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

}
