package com.chaomeng.baselib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.chaomeng.baselib.app.BaseApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 创建者     CJR
 * 创建时间   2019/9/3 16:41
 * 描述       工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Utils {

    /**Activity的生命周期监听*/
    private static final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();
    /**绑定了MainLooper的Handler, 用来发消息到主线程*/
    private static final Handler UTIL_HANDLER       = new Handler(Looper.getMainLooper());

    /**私有化构造函数, 禁止创建该类实例*/
    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化操作
     * @param application Application 实例
     */
    public static void init(final Application application) {
        application.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
    }

    /**
     * 运行在UI线程的Runnable
     * @param runnable Runnable实例
     */
    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            Utils.UTIL_HANDLER.post(runnable);
        }
    }

    /**
     * 延时发送到UI线程上的任务
     * @param runnable Runnable实例
     * @param delayMillis 延时, 单位ms
     */
    public static void runOnUiThreadDelayed(final Runnable runnable, long delayMillis) {
        Utils.UTIL_HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * app在前台时, 获取顶层的Activity; app不在前台或者获取不到顶层的Activity时, 返回Application
     * @return 顶层的Activity或者Application
     */
    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_LIFECYCLE.getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    public static Activity getTopActivity() {
        if (isAppForeground()) {
            return ACTIVITY_LIFECYCLE.getTopActivity();
        }
        return null;
    }

    /**
     * 获取Application
     * @return application实例
     */
    public static Application getApp() {
        return BaseApplication.getInstance();
    }

    /**
     * 获取Activity生命周期的监听者
     * @return Activity生命周期的监听者
     */
    public static ActivityLifecycleImpl getActivityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    /**
     * 获取当前App是否在前台显示
     * @return 当前App是否在前台显示
     */
    public static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName.equals(Utils.getApp().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Activity的生命周期事件监听者
     */
    static class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {

        /**Activity的栈*/
        final Stack<Activity> mActivityStack         = new Stack<>();
        /**Activity的销毁监听的map*/
        final HashMap<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMap = new HashMap<>();

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(final Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

        @Override
        public void onActivityDestroyed(Activity activity) {
            //  移除栈里的Activity
            mActivityStack.remove(activity);
            //  分发Activity的销毁事件
            dispatchOnActivityDestroyEvent(activity);
            //  移除Activity销毁事件的监听
            mDestroyedListenerMap.remove(activity);
        }

        /**
         * 获取顶层的Activity
         * @return 顶层的Activity
         */
        @SuppressLint("ObsoleteSdkInt")
        Activity getTopActivity() {
            if (!mActivityStack.isEmpty()) {
                for (int i = mActivityStack.size() - 1; i >= 0; i--) {
                    Activity activity = mActivityStack.get(i);
                    if (activity == null
                            || activity.isFinishing()
                            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                            && activity.isDestroyed())) {
                        continue;
                    }
                    return activity;
                }
            }
            return null;
        }

        /**
         * 移除Activity销毁事件的监听
         * @param activity 被监听的Activity
         */
        void removeOnActivityDestroyedListener(final Activity activity) {
            if (activity == null) {
                //  健壮性判断
                return;
            }
            //  移除Activity销毁事件的监听
            mDestroyedListenerMap.remove(activity);
        }

        /**
         * 添加Activity的销毁监听
         * @param activity 被监听销毁事件的Activity
         * @param listener 监听者
         */
        @SuppressWarnings("SameParameterValue")
        void addOnActivityDestroyedListener(Activity activity, OnActivityDestroyedListener listener) {
            if (activity == null || listener == null) {
                //  健壮性判断
                return;
            }
            Set<OnActivityDestroyedListener> listeners = mDestroyedListenerMap.get(activity);
            if (listeners == null) {
                //  Map中获取不到, 创建容器
                listeners = new HashSet<>();
                //  添加到Map中
                mDestroyedListenerMap.put(activity, listeners);
            }
            listeners.add(listener);
        }


        /**
         * 分发Activity的 销毁事件
         * @param activity 销毁的Activity
         */
        private void dispatchOnActivityDestroyEvent(Activity activity) {
            for (Map.Entry<Activity, Set<OnActivityDestroyedListener>> entry: mDestroyedListenerMap.entrySet()) {
                if (entry.getKey() == activity) {
                    //  找到要销毁的Activity, 记录监听容器
                    for (OnActivityDestroyedListener listener: entry.getValue()) {
                        //  根据监听者容器, 回调监听事件
                        listener.onActivityDestroyed(activity);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Activity的销毁监听
     */
    public interface OnActivityDestroyedListener {
        /**
         * Activity销毁中
         * @param activity 销毁的Activity
         */
        void onActivityDestroyed(Activity activity);
    }
}
