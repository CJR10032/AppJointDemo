package com.chaomeng.baselib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Field;

/**
 * 创建者     CJR
 * 创建时间   2019/09/03 16:07
 * 描述       从AndroidUtilCode里拷出来的Toast的帮助类, 处理了 用户没开通知权限部分手机toast不出来的问题,
 * *         和7.1.1Toast崩溃的问题
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class ToastUtils {

    /**当前页面的tag*/
    private static final String TAG = ToastUtils.class.getSimpleName();

    /**默认颜色*/
    private static final int    COLOR_DEFAULT = 0xFEFFFFFF;
    /**当Toast的内容为null时, 用该字符串赋值*/
    private static final String NULL          = "null";

    /**IToast实例*/
    private static IToast iToast;
    /**Toast内容的排版方式*/
    private static int    sGravity     = -1;
    /**Toast的x偏移*/
    private static int    sXOffset     = -1;
    /**Toast的y偏移*/
    private static int    sYOffset     = -1;
    /**Toast的背景色*/
    private static int    sBgColor     = COLOR_DEFAULT;
    /**Toast的背景资源id*/
    private static int    sBgResource  = -1;
    /**Toast内容的字体颜色*/
    private static int    sMsgColor    = COLOR_DEFAULT;
    /**Toast内容的字体大小*/
    private static int    sMsgTextSize = -1;

    /**私有化构造方法, 不允许创建该类的实例*/
    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    /**
     * Set the text size of message.
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        return showCustomShort(getView(layoutId));
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomShort(final View view) {
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        return showCustomLong(getView(layoutId));
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomLong(final View view) {
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the toast.
     */
    @SuppressWarnings("WeakerAccess")
    public static void cancel() {
        if (iToast != null) {
            iToast.cancel();
        }
    }

    /**
     * 显示Toast
     * @param resId 内容的资源id
     * @param duration Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
     */
    private static void show(final int resId, final int duration) {
        try {
            CharSequence text = Utils.getApp().getResources().getText(resId);
            show(text, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }
    }

    /**
     * 显示Toast(通过占位符设置内容)
     * @param resId  内容的资源id
     * @param duration  Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
     * @param args 占位符填充的内容
     */
    private static void show(final int resId, final int duration, final Object... args) {
        try {
            CharSequence text = Utils.getApp().getResources().getText(resId);
            String format = String.format(text.toString(), args);
            show(format, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }
    }

    /**
     * 显示Toast(通过占位符设置内容)
     * @param format 有占位符的字符串
     * @param duration  Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
     * @param args 占位符填充的内容
     */
    @SuppressWarnings("ConstantConditions")
    private static void show(final String format, final int duration, final Object... args) {
        String text;
        if (format == null) {
            text = NULL;
        } else {
            text = String.format(format, args);
            if (text == null) {
                text = NULL;
            }
        }
        show(text, duration);
    }

    /**
     * 显示Toast
     * @param text 要显示的内容
     * @param duration  Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
     */
    private static void show(final CharSequence text, final int duration) {
        Utils.runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.makeToast(Utils.getApp(), text, duration);
            final View toastView = iToast.getView();
            if (toastView == null) {
                return;
            }
            final TextView tvMessage = toastView.findViewById(android.R.id.message);
            if (sMsgColor != COLOR_DEFAULT) {
                tvMessage.setTextColor(sMsgColor);
            }
            if (sMsgTextSize != -1) {
                tvMessage.setTextSize(sMsgTextSize);
            }
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg(tvMessage);
            iToast.show();
        });
    }

    private static void show(final View view, final int duration) {
        Utils.runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.newToast(Utils.getApp());
            iToast.setView(view);
            iToast.setDuration(duration);
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg();
            iToast.show();
        });
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("ObsoleteSdkInt")
    private static void setBg() {
        if (sBgResource != -1) {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = iToast.getView();
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    /**
     * 设置TextView的背景
     * @param tvMsg 内容的TextView
     */
    private static void setBg(final TextView tvMsg) {
        if (sBgResource != -1) {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            final View toastView = iToast.getView();
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    /**
     * 根据布局Id填充View
     * @param layoutId 布局id
     * @return 填充的View
     */
    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate =
                (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(layoutId, null);
    }

    /**
     * Toast工厂
     */
    static class ToastFactory {

        /**
         * 创建Toast
         * @param context 上下文
         * @param text Toast要显示的内容
         * @param duration  Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
         * @return IToast实例
         */
        static IToast makeToast(Context context, CharSequence text, int duration) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                //  用户允许通知, 使用系统Toast
                return new SystemToast(makeNormalToast(context, text, duration));
            }
            //  用户不允许通知, 需要一些骚操作
            return new ToastWithoutNotification(makeNormalToast(context, text, duration));
        }

        /**
         * 创建Toast
         * @param context 上下文
         * @return IToast实例
         */
        static IToast newToast(Context context) {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                //  用户允许通知, 使用系统Toast
                return new SystemToast(new Toast(context));
            }
            //  用户不允许通知, 需要一些骚操作
            return new ToastWithoutNotification(new Toast(context));
        }

        /**
         * 创建普通Toast(系统原生Toast)
         * @param context 上下文
         * @param text Toast要显示的内容
         * @param duration Toast时长, 只有Toast.LENGTH_SHORT 和 Toast.LENGTH_LONG 两种
         * @return Toast实例
         */
        private static Toast makeNormalToast(Context context, CharSequence text, int duration) {
            @SuppressLint("ShowToast")
            Toast toast = Toast.makeText(context, "", duration);
            toast.setText(text);
            return toast;
        }
    }

    /**
     * 系统Toast, 内部包装的Toast
     */
    static class SystemToast extends AbsToast {

        SystemToast(Toast toast) {
            super(toast);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                //  7.1.1版本通过反射 对会崩溃的地方进行try catch
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(toast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) {/**/}
            }
        }

        @Override
        public void show() {
            mToast.show();
        }

        @Override
        public void cancel() {
            mToast.cancel();
        }

        /**
         * 安全的Handler, 用于转发Toast里的Handler消息
         */
        static class SafeHandler extends Handler {
            private Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(Message msg) {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                try {
                    impl.dispatchMessage(msg);
                } catch (Exception e) {
                    LogUtils.e(TAG, e.toString());
                }
            }
        }
    }

    static class ToastWithoutNotification extends AbsToast {

        private View mView;
        private WindowManager mWM;

        private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private static final Utils.OnActivityDestroyedListener LISTENER =
                new Utils.OnActivityDestroyedListener() {
                    @Override
                    public void onActivityDestroyed(Activity activity) {
                        if (iToast == null) return;
                        activity.getWindow().getDecorView().setVisibility(View.GONE);
                        iToast.cancel();
                    }
                };

        ToastWithoutNotification(Toast toast) {
            super(toast);
        }

        @Override
        public void show() {
            Utils.runOnUiThreadDelayed(this::realShow, 300);
        }

        private void realShow() {
            if (mToast == null) {
                //  健壮性判断
                return;
            }
            mView = mToast.getView();
            if (mView == null) {
                //  健壮性判断
                return;
            }
            final Context context = mToast.getView().getContext();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                Context topActivityOrApp = Utils.getTopActivityOrApp();
                if (!(topActivityOrApp instanceof Activity)) {
                    LogUtils.e(TAG, "Couldn't get top Activity.");
                    return;
                }
                Activity topActivity = (Activity) topActivityOrApp;
                if (topActivity.isFinishing() || topActivity.isDestroyed()) {
                    LogUtils.e(TAG, topActivity + " is useless");
                    return;
                }
                mWM = topActivity.getWindowManager();
                mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
                Utils.getActivityLifecycle().addOnActivityDestroyedListener(topActivity, LISTENER);
            }

            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.packageName = Utils.getApp().getPackageName();

            mParams.gravity = mToast.getGravity();
            if ((mParams.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((mParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }

            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();
            mParams.horizontalMargin = mToast.getHorizontalMargin();
            mParams.verticalMargin = mToast.getVerticalMargin();

            try {
                if (mWM != null) {
                    mWM.addView(mView, mParams);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Utils.runOnUiThreadDelayed(this::cancel
                    , mToast.getDuration() == Toast.LENGTH_SHORT ? 2000 : 3500);
        }

        @Override
        public void cancel() {
            try {
                if (mWM != null) {
                    mWM.removeViewImmediate(mView);
                }
            } catch (Exception ignored) {  }
            mView = null;
            mWM = null;
            mToast = null;
        }
    }

    static abstract class AbsToast implements IToast {

        Toast mToast;

        AbsToast(Toast toast) {
            mToast = toast;
        }

        @Override
        public void setView(View view) {
            mToast.setView(view);
        }

        @Override
        public View getView() {
            return mToast.getView();
        }

        @Override
        public void setDuration(int duration) {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId) {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            mToast.setText(s);
        }
    }

    @SuppressWarnings("unused")
    interface IToast {

        void show();

        void cancel();

        void setView(View view);

        View getView();

        void setDuration(int duration);

        void setGravity(int gravity, int xOffset, int yOffset);

        void setText(@StringRes int resId);

        void setText(CharSequence s);
    }
}