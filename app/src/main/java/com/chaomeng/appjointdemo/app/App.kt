package com.chaomeng.appjointdemo.app

import android.app.Application
import android.content.Intent
import com.chaomeng.appjointdemo.base.AbstractApp
import com.chaomeng.baselib.utils.LogUtils
import io.github.prototypez.appjoint.AppJoint
import io.github.prototypez.appjoint.core.AppSpec


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 11:35
 * 描述       壳工程的Application
 */
class App(
    application: Application,
    tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean,
    applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long,
    tinkerResultIntent: Intent
) : AbstractApp(
    application,
    tinkerFlags, tinkerLoadVerifyFlag,
    applicationStartElapsedTime,
    applicationStartMillisTime,
    tinkerResultIntent
) {

    /**伴生对象*/
    companion object {

        /**Application实例*/
        private lateinit var instance: Application

        /**
         * 获取Application实例
         *
         * @return Application实例
         */
        fun get() = instance
    }

    override fun onCreate() {
        //  因为这个类是Tinker代理过来的, 所以我们需要手动调用AppJoint.get().attachBaseContext, 否则子模块会拿不到Application
        //  AppJoint.get().attachBaseContext(application)
        super.onCreate()
        instance = application

        LogUtils.e("app init is called")
    }

}