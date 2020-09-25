package com.chaomeng.appjointdemo.app

import com.chaomeng.baselib.app.BaseApplication
import com.chaomeng.baselib.utils.LogUtils
import io.github.prototypez.appjoint.core.AppSpec


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 16:55
 * 描述       壳工程的application
 */
@AppSpec
class CmApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.e("app init is called")
    }

}