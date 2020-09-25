package com.chaomeng.baselib.inter;

import android.view.View;

/**
 * 创建者     CJR
 * 创建时间   2019/8/14 09:10
 * 描述       列表的条目点击监听
 */
public interface OnItemClickListener {

    /**
     * 条目被点击了
     *
     * @param view     被点击了的View
     * @param position 被点击条目的位置
     */
    void onItemClick(View view, int position);
}
