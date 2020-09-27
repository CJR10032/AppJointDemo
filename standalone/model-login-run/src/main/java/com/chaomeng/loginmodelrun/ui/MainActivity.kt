package com.chaomeng.loginmodelrun.ui

import com.chaomeng.baselib.base.BaseActivity
import com.chaomeng.loginmodelrun.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 创建      CJR
 * 创建时间  2020/09/25 16:49
 * 描述      登录模块的测试页面
 */
class MainActivity : BaseActivity() {

    override fun onContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun initData() {
    }

    override fun initListener() {
        //  跳转登录页面按钮的点击事件
        btnTurnToLogin.setOnClickListener {

        }
    }

}
