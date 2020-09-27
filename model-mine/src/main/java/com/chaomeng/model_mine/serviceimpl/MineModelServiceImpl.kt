package com.chaomeng.model_mine.serviceimpl

import android.content.Context
import android.content.Intent
import com.chaomeng.model_mine.ui.MineCenterActivity
import com.chaomeng.router.service.mine.MineModelService


/**
 * 创建者     CJR
 * 创建时间   2020/9/27 10:02
 * 描述       "我的"模块接口的实现
 */
class MineModelServiceImpl : MineModelService {

    override fun startMineActivity(ctx: Context) {
        ctx.startActivity(Intent(ctx, MineCenterActivity::class.java))
    }

}