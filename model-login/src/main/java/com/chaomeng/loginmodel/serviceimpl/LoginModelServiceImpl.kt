package com.chaomeng.loginmodel.serviceimpl

import android.content.Context
import android.content.Intent
import com.chaomeng.loginmodel.ui.MainActivity
import com.chaomeng.router.service.login.LoginModelService
import io.github.prototypez.appjoint.core.ServiceProvider


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:26
 * 描述       登录模块接口的实现
 */
@ServiceProvider
open class LoginModelServiceImpl : LoginModelService {

    override fun startLoginActivity(ctx: Context) {
        //  跳转登录页面
        ctx.startActivity(Intent(ctx, MainActivity::class.java))
    }
}