package com.chaomeng.loginmodel.ui

import android.widget.Toast
import com.chaomeng.baselib.base.BaseActivity
import com.chaomeng.baselib.utils.LogUtils
import com.chaomeng.baselib.utils.ToolsUtil
import com.chaomeng.loginmodel.R
import io.github.prototypez.appjoint.AppJoint
import kotlinx.android.synthetic.main.login_activity_main.*

/**
 * 创建      CJR
 * 创建时间  2020/09/25 16:49
 * 描述      登录页面
 */
class MainActivity : BaseActivity() {

    override fun onContentView() {
        setContentView(R.layout.login_activity_main)
    }

    override fun initData() {
    }

    override fun initListener() {
        //  显示Toast的点击事件
        btnToast.setOnClickListener {
            val msg = "model size = ${AppJoint.get().moduleApplications()?.size}"
            LogUtils.e(msg)
            Toast.makeText(
                this,
                "${btnToast.text}被点击了~$msg", Toast.LENGTH_SHORT
            ).show()
        }

        //  测试application是否初始化了
        btnTestAppInit.setOnClickListener {
            ToolsUtil.doToast("${btnTestAppInit.text}被点击了~")
        }
    }

}
