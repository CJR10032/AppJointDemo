package com.chaomeng.model_mine_run.ui

import com.chaomeng.baselib.base.BaseActivity
import com.chaomeng.model_mine_run.R
import com.chaomeng.model_mine_run.serviceimpl.Services
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 创建      CJR
 * 创建时间  2020/09/27 10:34
 * 描述      "我的"组件的测试工程
 */
class MainActivity : BaseActivity() {

    override fun onContentView() {
        setContentView(R.layout.login_activity_login)
    }

    override fun initData() {
    }

    override fun initListener() {
        //  跳转登录页面
        btnTurnToLogin.setOnClickListener {
            Services.loginModelService.startLoginActivity(this) {
                showMessage("result = $it")
            }
        }

        //  跳转个人中心
        btnTurnToMineCenter.setOnClickListener {
            Services.mineModelService.startMineActivity(this)
        }
    }

}
