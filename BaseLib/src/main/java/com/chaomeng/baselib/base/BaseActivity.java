package com.chaomeng.baselib.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chaomeng.baselib.utils.LogUtils;
import com.chaomeng.baselib.utils.ToolsUtil;


/**
 * 创建者     CJR
 * 创建时间   2019/7/3 10:11
 * 描述       Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onContentView();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelToast();
    }

    /**填充layout*/
    protected abstract void onContentView();

    /**初始化view*/
    protected void initView() {

    }

    /**初始化数据*/
    protected abstract void initData();
    /**初始化监听*/
    protected abstract void initListener();

    /**TODO 用于demo, 方便输出信息的*/
    public void showMessage(String msg) {
        LogUtils.log(msg);
        doToast(msg);
    }

    /**
     * 默认都实现了点击事件
     * @param v 被点击的view
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * 启动Acitivity
     * @param cls Activity 的class
     */
    public void startActivity(Class cls) {
        startActivity(new Intent(this, cls));
    }

    //  --------------start 弹toast-------------

    /**
     * 显示toast
     * @param s toast的内容
     */
    @SuppressLint("ShowToast")
    public void doToast(String s) {
        ToolsUtil.doToast(s);
    }


    /**
     * 显示toast
     * @param stringRes 内容的资源id
     */
    public void doToast(int stringRes) {
        ToolsUtil.doToast(stringRes);
    }

    /**取消toast*/
    public void cancelToast() {
        ToolsUtil.cancelToast();
    }

    //  --------------end 弹toast-------------

    //------- start 点击空白处隐藏软键盘 -------------------

    /**
     *  add CJR 点击空白隐藏软键盘
     * @param ev 事件
     * @return 是否处理事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null && isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v 事件发生的view
     * @param event 事件
     * @return 是否隐藏键盘
     */
    protected boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            // 点击EditText之外的事件, 隐藏软键盘
            return event.getX() <= left || event.getX() >= right
                    || event.getY() <= top || event.getY() >= bottom;
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，
        // 第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token token
     */
    protected void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
    //------- end 点击空白处隐藏软键盘 -------------------
}
