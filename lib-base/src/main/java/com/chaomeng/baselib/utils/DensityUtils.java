package com.chaomeng.baselib.utils;

import android.content.res.Resources;

/**
 * 创建者     CJR
 * 创建时间   2019/8/15 15:08
 * 描述       与屏幕分辨率相关的工具类
 */
public class DensityUtils {

    /**
     * 获取density的值
     *
     * @return density的值
     */
    private static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * px转换为dip
     *
     * @param pxValue px的值
     * @return float类型的值
     */
    public static float px2dipF(int pxValue) {
        return pxValue / getDensity();
    }

    public static int px2dip(int pxValue) {
        return (int) (pxValue / getDensity() + 0.5f);
    }

    /**
     * dip转换为px
     *
     * @param dipValue dip的值
     * @return float类型的值
     */
    public static float dip2pxF(float dipValue) {
        return dipValue * getDensity();
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * getDensity() + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取状态栏高度
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height"
                , "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
