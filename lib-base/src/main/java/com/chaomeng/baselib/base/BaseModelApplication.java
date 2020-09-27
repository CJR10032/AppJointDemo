package com.chaomeng.baselib.base;

import android.app.Application;

/**
 * 创建者     CJR
 * 创建时间   2019/7/31 10:22
 * 描述       组件的程序入口
 */
public class BaseModelApplication extends Application {

    /**静态实例*/
    protected static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = (Application) getApplicationContext();
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
