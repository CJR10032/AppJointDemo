package com.chaomeng.loginmodel.app

import com.chaomeng.baselib.app.BaseApplication
import com.chaomeng.baselib.utils.LogUtils
import io.github.prototypez.appjoint.core.ModuleSpec


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 16:53
 * 描述       登录组件的application
 */
@ModuleSpec(priority = 1)
open class LoginApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.e("LoginModel init is called")
    }

}