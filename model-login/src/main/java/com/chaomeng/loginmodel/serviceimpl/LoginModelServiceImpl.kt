package com.chaomeng.loginmodel.serviceimpl

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.chaomeng.loginmodel.ui.LoginActivity
import com.chaomeng.router.service.login.LoginModelService
import io.github.prototypez.appjoint.core.ServiceProvider


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:26
 * 描述       登录模块接口的实现
 */
@ServiceProvider
open class LoginModelServiceImpl : LoginModelService {


    override fun startLoginActivity(
        activity: ComponentActivity,
        callback: (userName: String) -> Unit
    ) {
        //  注册监听回调
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                //  回调页面返回结果
                callback.invoke(it.data?.getStringExtra(LoginActivity.USER_NAME_EXTRA) ?: "")
            }
        }
            //  跳转页面
            .launch(Intent(activity, LoginActivity::class.java))

    }
}