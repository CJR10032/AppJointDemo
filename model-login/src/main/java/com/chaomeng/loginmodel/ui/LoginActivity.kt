package com.chaomeng.loginmodel.ui

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.chaomeng.baselib.base.BaseActivity
import com.chaomeng.baselib.utils.LogUtils
import com.chaomeng.baselib.utils.ToolsUtil
import com.chaomeng.loginmodel.R
import io.github.prototypez.appjoint.AppJoint
import kotlinx.android.synthetic.main.login_activity_login.*

/**
 * 创建      CJR
 * 创建时间  2020/09/25 16:49
 * 描述      登录页面
 */
class LoginActivity : BaseActivity() {

    /**伴生对象*/
    companion object {

        /**通过intent写带用户名的key值*/
        const val USER_NAME_EXTRA = "user_name_extra"

    }

    override fun onContentView() {
        setContentView(R.layout.login_activity_login)
    }

    override fun initData() {

    }

    override fun initListener() {
        //  显示Toast的点击事件
        btnToast.setOnClickListener {
            val msg = "model size = ${AppJoint.get().moduleApplications()?.size}"
            LogUtils.e(msg)
            Toast.makeText(
                this,
                "${btnToast.text}被点击了~$msg", Toast.LENGTH_SHORT
            ).show()
        }

        //  测试application是否初始化了
        btnTestAppInit.setOnClickListener {
            ToolsUtil.doToast("${btnTestAppInit.text}被点击了~")
        }

        //  携带数据返回
        btnTurnBack.setOnClickListener {
            //  设置返回携带的数据
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(USER_NAME_EXTRA, "kirino")
            })
            //  结束当前页面
            finish()
        }
    }

}
