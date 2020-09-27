package com.chaomeng.model_mine.app

import com.chaomeng.baselib.base.BaseModelApplication
import com.chaomeng.baselib.utils.LogUtils
import io.github.prototypez.appjoint.core.ModuleSpec


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 16:53
 * 描述       "我的"组件的application
 */
@ModuleSpec(priority = 1)
open class MineApplication : BaseModelApplication() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.e("LoginModel init is called")
    }

}