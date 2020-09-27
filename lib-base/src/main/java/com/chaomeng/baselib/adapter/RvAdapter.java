package com.chaomeng.baselib.adapter;

import androidx.annotation.NonNull;

import com.chaomeng.baselib.R;
import com.chaomeng.baselib.base.BaseRvAdapter;
import com.chaomeng.baselib.base.BaseViewHolder;

/**
 * 创建者     CJR
 * 创建时间   2019/8/15 16:36
 * 描述
 */
public class RvAdapter extends BaseRvAdapter<String> {

    @Override
    public int getItemLayoutResId() {
        return R.layout.view_rv_item;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        //  条目的TextView设置数据
        holder.setText(R.id.tv_name, mDataList.get(position));
    }
}
