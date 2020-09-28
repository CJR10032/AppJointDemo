package com.chaomeng.appjointdemo.utils

import android.app.Application
import com.chaomeng.baselib.utils.LogUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.interfaces.BetaPatchListener

/**
 * 更新      CJR
 * 更新时间  2020/07/29
 * 更新信息  使用bugly的初始化方式
 */
class BuglyUtil {

    /**伴生对象*/
    companion object {

        /**
         * 初始化
         *
         * @param app      application实例
         * @param isDebug  是否debug模式
         */
        fun init(app: Application, isDebug: Boolean) {
            LogUtils.e("初始化bugly相关配置")

            // 补丁回调接口
            Beta.betaPatchListener = object : BetaPatchListener {
                override fun onPatchReceived(patchFile: String) {
                    LogUtils.e("补丁下载地址：$patchFile")
                }

                override fun onDownloadReceived(savedLength: Long, totalLength: Long) {
                    LogUtils.e("savedLength = ${savedLength}; totalLength = $totalLength")
                }

                override fun onDownloadSuccess(msg: String) {
                    LogUtils.e("补丁下载成功")
                }

                override fun onDownloadFailure(msg: String) {
                    LogUtils.e("补丁下载失败")
                }

                override fun onApplySuccess(msg: String) {
                    LogUtils.e("补丁应用成功")
                }

                override fun onApplyFailure(msg: String) {
                    LogUtils.e("补丁应用失败")
                }

                override fun onPatchRollback() {}
            }

            //  设置开发设备
            Bugly.setIsDevelopmentDevice(app, true)
            if (isDebug) {
                Bugly.init(app, "c446ae04d0", true)
            } else {
                Bugly.init(app, "c446ae04d0", true)
            }
        }
    }
}