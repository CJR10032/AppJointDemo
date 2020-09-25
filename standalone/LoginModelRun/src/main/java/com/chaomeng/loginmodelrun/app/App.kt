package com.chaomeng.loginmodelrun.app

import com.chaomeng.baselib.utils.LogUtils
import com.chaomeng.loginmodel.app.LoginApplication


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:11
 * 描述       登录测试工程的application
 */
class App : LoginApplication() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.e("LoginModelRun init is called")
    }

}