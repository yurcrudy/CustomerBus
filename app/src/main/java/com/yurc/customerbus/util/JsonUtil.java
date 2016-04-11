package com.yurc.customerbus.util;

import com.google.gson.Gson;

/**
 * Date：2016/4/11
 * Author：yurc
 * Describe：用于json 转换
 */
public class JsonUtil {
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }


    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 当result为空时(result:""), 转为gson能解析的格式: result:[]
     * @param json
     * @return jsonFormatted
     * */
    public static String formatJson(String json){
        String jsonFormatted = json.replace("\"result\":\"\"", "\"result\":[]");
        return jsonFormatted;
    }
}
