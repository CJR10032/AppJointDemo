package com.chaomeng.model_mine_run.serviceimpl

import com.chaomeng.loginmodel.serviceimpl.LoginModelServiceImpl
import com.chaomeng.model_mine.serviceimpl.MineModelServiceImpl
import com.chaomeng.router.service.login.LoginModelService
import com.chaomeng.router.service.mine.MineModelService


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:30
 * 描述       其他组件提供的接口的集合(用到哪个组件的就调添加哪个组件的)
 */
object Services {

    /**
     * 登录组件提供的接口
     */
    val loginModelService: LoginModelService = LoginModelServiceImpl()

    /**
     * '我的'组件提供的接口
     */
    val mineModelService: MineModelService = MineModelServiceImpl()

}