package com.chaomeng.appjointdemo.base

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import com.chaomeng.appjointdemo.BuildConfig
import com.chaomeng.appjointdemo.utils.BuglyUtil
import com.chaomeng.baselib.utils.LogUtils
import com.chaomeng.baselib.utils.Utils
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.entry.DefaultApplicationLike


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 11:34
 * 描述       App的抽象类, 因为其他渠道比如Flutter渠道也需要做初始化, 这里抽取共性
 */
abstract class AbstractApp(
    application: Application,
    tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean,
    applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long,
    tinkerResultIntent: Intent
) : DefaultApplicationLike(
    application,
    tinkerFlags, tinkerLoadVerifyFlag,
    applicationStartElapsedTime,
    applicationStartMillisTime,
    tinkerResultIntent
) {
    override fun onCreate() {
        super.onCreate()

        LogUtils.e("app init is called")
        //  初始化Bugly
        BuglyUtil.init(application, BuildConfig.DEBUG)
        //  初始化Sp工具类
        Utils.init(application)
    }

    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)

        //  you must install multiDex whatever tinker is installed!
        MultiDex.install(base)
        //  安装tinker
        Beta.installTinker(this)
    }
}