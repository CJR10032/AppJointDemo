package com.chaomeng.baselib.app;

import android.app.Application;

import com.chaomeng.baselib.utils.Utils;

/**
 * 创建者     CJR
 * 创建时间   2019/7/31 10:22
 * 描述       程序入口
 */
public class BaseApplication extends Application {

    /**静态实例*/
    protected static BaseApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Utils.init(this);
    }

    /**获取 KoApplication 实例*/
    public static BaseApplication getInstance() {
        return sInstance;
    }

}
