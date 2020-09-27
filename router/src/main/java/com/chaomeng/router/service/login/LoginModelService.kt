package com.chaomeng.router.service.login

import android.content.Context


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:23
 * 描述       登录组件暴露给外部的接口
 */
interface LoginModelService {

    /**
     * 跳转登录页面的方法
     *
     * @param ctx Context实例
     */
    fun startLoginActivity(ctx: Context)
}