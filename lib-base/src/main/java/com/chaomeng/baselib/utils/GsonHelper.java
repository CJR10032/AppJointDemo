package com.chaomeng.baselib.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 创建者     CJR
 * 创建时间   2017-10-11 16:41
 * 描述       转换Json的帮助类
 */
public class GsonHelper {

    /**获取Gson实例*/
    public static Gson getGson() {
        return GsonHolder.sGson;
    }

    /**
     * 把Object转为json格式的String
     * @param object 转换成json的obj
     * @return json类型的String
     */
    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 把Json字符串转为Object
     * @param json json字符串
     * @param cls Object的class
     * @param <T> 泛型
     * @return 指定类型的Object
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return getGson().fromJson(json, cls);
    }

    /**
     * 把Json字符串转为Object
     * @param json  json字符串
     * @param typeOfT Object的classType
     * @param <T> 泛型
     * @return 指定类型的Object
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        return getGson().fromJson(json, typeOfT);
    }

    /**使用静态内部类保证懒汉单例*/
    private static class GsonHolder {
        /**gson的实例*/
        private static final Gson sGson = new Gson();
    }
}
