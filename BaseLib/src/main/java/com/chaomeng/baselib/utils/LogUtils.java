package com.chaomeng.baselib.utils;

import android.util.Log;


/**
 * 创建者     CJR
 * 创建时间   2017-09-19 16:35
 * 描述       简单的日志工具类
 */
public class LogUtils {

    /**当前类的tag*/
    private static final String TAG = LogUtils.class.getSimpleName();

    /**是否开启日志的标识*/
    private static final boolean IS_LOG = true;

    /**
     * 等级debug, tag:LogUtils, 默认输出的log
     *
     * @param msg log的内容
     */
    public static void log(String msg) {
        log(Log.ERROR, TAG, msg);
    }

    /**
     * 等级debug, tag:netLog, 用于网络输出的log
     *
     * @param msg log的内容
     */
    public static void netLog(String msg) {
        log(Log.ERROR, "netLog", msg);
    }

    /**
     * verbose等级的log
     *
     * @param msg 日志的内容
     */
    public static void v(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.VERBOSE, getClassName(), msg);
    }

    /**
     * debug等级的log
     *
     * @param msg 日志的内容
     */
    public static void d(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.DEBUG, getClassName(), msg);
    }

    /**
     * info等级的log
     *
     * @param msg 日志的内容
     */
    public static void i(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.INFO, getClassName(), msg);
    }

    /**
     * warn等级的log
     *
     * @param msg 日志的内容
     */
    public static void w(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.WARN, getClassName(), msg);
    }

    /**
     * error等级的log
     *
     * @param msg 日志的内容
     */
    public static void e(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.ERROR, getClassName(), msg);
    }

    /**
     * assert等级的log
     *
     * @param msg 日志的内容
     */
    public static void wtf(String msg) {
        if (!IS_LOG) {
            //  不输出日志
            return;
        }
        log(Log.ASSERT, getClassName(), msg);
    }

    /**
     * verbose等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void v(String tag, String msg) {
        log(Log.VERBOSE, tag, msg);
    }

    /**
     * debug等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void d(String tag, String msg) {
        log(Log.DEBUG, tag, msg);
    }

    /**
     * info等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void i(String tag, String msg) {
        log(Log.INFO, tag, msg);
    }

    /**
     * warn等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void w(String tag, String msg) {
        log(Log.WARN, tag, msg);
    }

    /**
     * error等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void e(String tag, String msg) {
        log(Log.ERROR, tag, msg);
    }

    /**
     * assert等级的log
     *
     * @param tag 日志的tag
     * @param msg 日志的内容
     */
    public static void wtf(String tag, String msg) {
        log(Log.ASSERT, tag, msg);
    }

    /**
     * 打印日志
     *
     * @param level 日志等级
     * @param tag 日志的tag
     * @param msg 日志的信息
     */
    private static void log(int level, String tag, String msg) {
        //  允许输出日志, tag不为null也不是空字符, msg不为null的时候处理
        if (IS_LOG) {
            msg = getMethodName() + "--" + msg;
            //  健壮性判断, tag为null时设置空字符串, msg为null时给null字符串
            tag = tag == null ? "" : tag;
            msg = msg == null ? "null" : msg;

            msg = msg.trim();
            int index = 0;
            int maxLength = 3800;
            String sub;
            while (index < msg.length()) {
                // java的字符不允许指定超过总的长度end
                if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
                } else {
                    sub = msg.substring(index, maxLength + index);
                }

                index += maxLength;
                levelLog(level, tag, sub.trim());
            }
        }
    }

    /**
     * 打印不同等级的log
     *
     * @param level log的等级
     */
    private static void levelLog(int level, String tag, String msg) {
        switch (level) {
            case Log.VERBOSE:
                //  verbose等级的log
                Log.v(tag, msg);
                break;
            case Log.DEBUG:
                //  debug等级的log
                Log.d(tag, msg);
                break;
            case Log.INFO:
                //  info等级的log
                Log.i(tag, msg);
                break;
            case Log.WARN:
                //  warn等级的log
                Log.w(tag, msg);
                break;
            case Log.ERROR:
                //  error等级的log
                Log.e(tag, msg);
                break;
            case Log.ASSERT:
                //  assert等级的log
                Log.wtf(tag, msg);
                break;
            default:
                //  默认info等级的log
                Log.i(tag, msg);
                break;
        }
    }

    /**
     * 获取调用该日志方法的方法名
     *
     * @return 调用该日志方法的方法名
     */
    private static String getMethodName() {
        StackTraceElement[] frames =  (new Throwable()).getStackTrace();
        return frames[3].getMethodName();
    }

    /**
     * 获取调用日志工具类的类名
     *
     * 参考: https://blog.csdn.net/feifuzeng/article/details/78069973
     * 用StackTraceElement[] frames = Thread.currentThread().getStackTrace()的话取的是frames[4]
     *
     * 参考: https://android.googlesource.com/platform/tools/tradefederatio
     * android-6.0.0_r2/src/com/android/tradefed/log/LogUtil.java
     *
     * @return 调用日志工具类的类名
     */
    private static String getClassName() {
        StackTraceElement[] frames = (new Throwable()).getStackTrace();
        return parseClassName(frames[2].getClassName());
    }

    /**
     * 参考: https://android.googlesource.com/platform/tools/tradefederation/+/
     * android-6.0.0_r2/src/com/android/tradefed/log/LogUtil.java
     *
     * @param fullName 从StackTraceElement中获取的完整名称
     *
     * @return 调用日志工具类的类名
     */
    private static String parseClassName(String fullName) {
        int lastDot = fullName.lastIndexOf('.');
        String simpleName = fullName;
        if (lastDot != -1) {
            simpleName = fullName.substring(lastDot + 1);
        }
        // handle inner class names
        int lastDollar = simpleName.lastIndexOf('$');
        if (lastDollar != -1) {
            simpleName = simpleName.substring(0, lastDollar);
        }
        return simpleName;
    }

}
