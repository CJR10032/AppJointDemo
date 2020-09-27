package com.chaomeng.router.service.mine

import android.content.Context


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 9:59
 * 描述       个人中心模块对外暴露的接口
 */
interface MineModelService {

    /**
     * 跳转"我的"页面的方法
     *
     * @param ctx Context实例
     */
    fun startMineActivity(ctx: Context)
}