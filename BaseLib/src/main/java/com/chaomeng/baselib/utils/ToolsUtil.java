package com.chaomeng.baselib.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.chaomeng.baselib.app.BaseApplication;

/**
 * 创建者     CJR
 * 创建时间   2019/8/15 15:08
 * 描述       工具类
 */
public class ToolsUtil {

    /**toast的实例*/
    private static Toast mToast;

    /**
     * 显示toast
     *
     * @param s toast的内容
     */
    @SuppressLint("ShowToast")
    public static void doToast(String s) {
        cancelToast();
        if (s == null) {
            return;
        }
        mToast = Toast.makeText(BaseApplication.getInstance(),
                null, Toast.LENGTH_SHORT);
        mToast.setText(s);
        //  显示toast信息
        mToast.show();
    }


    /**
     * 显示toast
     *
     * @param stringRes 内容的资源id
     */
    public static void doToast(int stringRes) {
        String str;
        try {
            str = BaseApplication.getInstance()
                    .getResources().getString(stringRes);
        } catch (Exception ignore) {
            str = String.valueOf(stringRes);
        }
        doToast(str);
    }

    /**取消toast*/
    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
