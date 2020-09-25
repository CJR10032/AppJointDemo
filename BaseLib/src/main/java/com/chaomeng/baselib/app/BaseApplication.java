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
    protected static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = (Application) getApplicationContext();
        Utils.init(sInstance);
    }

    /**
     * 获取 Application 实例
     *
     * @return Application实例
     */
    public static Application getInstance() {
        return sInstance;
    }

}
