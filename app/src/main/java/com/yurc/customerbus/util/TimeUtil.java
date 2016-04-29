package com.yurc.customerbus.util;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Date：2016/4/15
 * Author：yurc
 * Describe：时间工具类
 */
public class TimeUtil {

    public static final long MILLISECONDS_IN_A_DAY = 24 * 60 * 60 * 1000;

    public static final long MILLISECONDS_IN_A_HOUR = 60 * 60 * 1000;

    public static final String HOUR_MINUTE = "HH:mm";

    public static final String GMT_MILLISECONDS_DATE_ONLY = "GMTMillisecondsDateOnly";
    public static final String GMT_MILLISECONDS = "GMTMilliseconds";

    public static String dateToString(Date time, String timeFormat) {
        if (time == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormat);
        return formatter.format(time);
    }
}
