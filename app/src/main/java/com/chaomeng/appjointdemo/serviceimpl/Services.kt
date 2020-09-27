package com.chaomeng.appjointdemo.serviceimpl

import com.chaomeng.router.service.login.LoginModelService
import com.chaomeng.router.service.mine.MineModelService
import io.github.prototypez.appjoint.AppJoint


/**
 * 创建者     CJR
 * 创建时间   2020/9/25 17:30
 * 描述       其他组件提供的接口的集合(用到哪个组件的就调添加哪个组件的)
 */
object Services {

    /**
     * 登录组件提供的接口
     */
    val loginModelService: LoginModelService = AppJoint.service(LoginModelService::class.java)

    /**
     * 我的组件提供的接口
     */
    val mineModelService: MineModelService = AppJoint.service(MineModelService::class.java)

}