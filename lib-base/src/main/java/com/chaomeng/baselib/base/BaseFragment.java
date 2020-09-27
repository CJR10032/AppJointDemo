package com.chaomeng.baselib.base;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chaomeng.baselib.utils.ToolsUtil;

import org.jetbrains.annotations.NotNull;


/**
 * 创建者     CJR
 * 创建时间   2019-07-25 09:04
 * 描述       Fragment的基类
 */
@SuppressWarnings("unused")
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable
                                     Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        initListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelToast();
    }

    /**填充layout*/
    protected abstract int getLayoutId();

    /**初始化视图*/
    protected abstract void initView();

    /**初始化数据*/
    protected abstract void initData();

    @SuppressWarnings("SameParameterValue")
    protected View findViewById(@IdRes int id) {
        if (getView() == null) {
            return null;
        }
        return getView().findViewById(id);
    }

    /**初始化监听事件*/
    protected abstract void initListener();

    /**
     * 用于在replace或者add的时候标识当前的Fragment,
     *  一般传fragment的class.getSimpleName
     */
    public abstract String getFragmentTag();


    /**
     * 显示toast
     * @param contents toast的内容
     */
    @SuppressLint("ShowToast")
    public void doToast(String contents) {
        ToolsUtil.doToast(contents);
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
}
