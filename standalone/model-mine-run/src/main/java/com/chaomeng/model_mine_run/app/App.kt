package com.chaomeng.model_mine_run.app

import com.chaomeng.baselib.utils.LogUtils
import com.chaomeng.model_mine.app.MineApplication


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 10:23
 * 描述
 */
class App : MineApplication() {

    override fun onCreate() {
        super.onCreate()

        LogUtils.e("MineModelRun init is called")
    }

}