package com.chaomeng.appjointdemo.ui

import com.chaomeng.appjointdemo.R
import com.chaomeng.appjointdemo.serviceimpl.Services
import com.chaomeng.baselib.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 创建      CJR
 * 创建时间  2020/09/25 15:18
 * 描述      测试AppJoint的工程
 */
class MainActivity : BaseActivity() {

    override fun onContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun initData() {
    }

    override fun initListener() {
        //  跳转登录页面
        btnTurnToLogin.setOnClickListener {
            //  跳转登录页面
            Services.loginModelService.startLoginActivity(this) {
                showMessage("result = $it")
            }
        }

        //  跳转个人中心页面
        btnTurnToMineCenter.setOnClickListener {
            //  跳转个人中心
            Services.mineModelService.startMineActivity(this)
        }
    }
}