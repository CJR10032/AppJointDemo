package com.chaomeng.router.service.login

import androidx.activity.ComponentActivity


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:23
 * 描述       登录组件暴露给外部的接口
 */
interface LoginModelService {

    /**
     * 跳转登录页面的方法
     *
     * @param activity ComponentActivity实例
     * @param callback 登录页面返回的结果, userName表示用户名
     */
    fun startLoginActivity(
        activity: ComponentActivity,
        callback: (userName: String) -> Unit
    )
}