package com.chaomeng.baselib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.chaomeng.baselib.base.BaseModelApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 创建者     CJR
 * 创建时间   2019/9/24 17:03
 * 描述       状态栏的工具类, 主要完成沉浸式, 修改状态栏文字颜色等
 */
@SuppressWarnings("unused")
public final class BarUtil {

    /**statusBar的tag*/
    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";

    /**
     * 私有构造函数, 不允许创建该类实例
     */
    private BarUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 要设置状态栏颜色的Activity
     * @param color    状态栏色颜色
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color) {
        return setStatusBarColor(activity, color, false);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 要设置状态栏颜色的Activity
     * @param color    状态栏色颜色
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     * @return StatusBar的View
     */
    public static View setStatusBarColor(@NonNull final Activity activity,
                                         @ColorInt final int color,
                                         final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //  4.4以下没法处理
            return null;
        }
        transparentStatusBar(activity);
        return applyStatusBarColor(activity, color, isDecor);
    }

    /**
     * 转换状态栏
     *
     * @param activity Activity实例
     */
    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int vis = window.getDecorView().getSystemUiVisibility() &
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(option | vis);
            } else {
                window.getDecorView().setSystemUiVisibility(option);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 要设置状态栏颜色的Activity
     * @param color    状态栏色颜色
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     * @return StatusBar的View
     */
    private static View applyStatusBarColor(final Activity activity,
                                            final int color,
                                            boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            fakeStatusBarView = createStatusBarView(activity, color);
            parent.addView(fakeStatusBarView);
        }
        return fakeStatusBarView;
    }

    /**
     * 创建StatusBar的View
     *
     * @param activity Activity实例
     * @param color    状态栏的颜色
     * @return StatusBar的View
     */
    private static View createStatusBarView(final Activity activity,
                                            final int color) {
        View statusBarView = new View(activity);
        statusBarView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(color);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏的高度
     */
    @SuppressWarnings("WeakerAccess")
    public static int getStatusBarHeight() {
        Resources resources = BaseModelApplication.getInstance().getResources();
        int resourceId = resources.getIdentifier("status_bar_height"
                , "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 设置状态栏的模式 light mode.
     *
     * @param activity    要修改状态栏模式的Activity
     * @param isLightMode true设置为 light mode (黑色图标和文字); false 白色文字
     */
    public static void setStatusBarLightMode(@NonNull Activity activity, boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * 设置状态栏的模式 light mode.
     *
     * @param window      要修改状态栏模式Activity的Window
     * @param isLightMode true设置为 light mode (黑色图标和文字); false 白色文字
     */
    @SuppressWarnings("UnnecessaryReturnStatement")
    private static void setStatusBarLightMode(@NonNull Window window, boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  6.0 以上的系统, 直接调用系统的api
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isLightMode) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //  4.4 以上 6.0 以下的系统, 小米和魅族有提供方法修改亮色模式
            if (setMiUiStatusBarLightMode(window, isLightMode)) {
                //  小米手机的状态栏修改
                return;
            }
            if (setFlymeStatusBarLightMode(window, isLightMode)) {
                //  魅族手机的状态栏修改
                return;
            }
        }
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window      需要设置的窗口
     * @param isLightMode 是否把状态栏字体及图标颜色设置为亮色模式 (黑色图标和文字)
     * @return boolean    成功执行返回true
     */
    @SuppressWarnings("unchecked")
    private static boolean setMiUiStatusBarLightMode(Window window, boolean isLightMode) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                @SuppressLint("PrivateApi") Class layoutParams =
                        Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (isLightMode) {
                    //  状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //  清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;
            } catch (Exception ignore) {
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window      需要设置的窗口
     * @param isLightMode 是否把状态栏字体及图标颜色设置为亮色模式 (黑色图标和文字)
     * @return boolean    成功执行返回true
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static boolean setFlymeStatusBarLightMode(Window window, boolean isLightMode) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (isLightMode) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception ignore) {
            }
        }
        return result;
    }
}
