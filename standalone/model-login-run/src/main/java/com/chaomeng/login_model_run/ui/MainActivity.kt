package com.chaomeng.login_model_run.ui

import com.chaomeng.baselib.base.BaseActivity
import com.chaomeng.login_model_run.R
import com.chaomeng.login_model_run.serviceimpl.Services
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 创建      CJR
 * 创建时间  2020/09/25 16:49
 * 描述      登录模块的测试页面
 */
class MainActivity : BaseActivity() {

    override fun onContentView() {
        setContentView(R.layout.login_activity_login)
    }

    override fun initData() {
    }

    override fun initListener() {
        //  跳转登录页面按钮的点击事件
        btnTurnToLogin.setOnClickListener {
            Services.loginModelService.startLoginActivity(this) {
                showMessage("result = $it")
            }
        }
    }

}
