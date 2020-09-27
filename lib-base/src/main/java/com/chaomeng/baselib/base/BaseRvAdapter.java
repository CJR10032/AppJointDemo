package com.chaomeng.baselib.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chaomeng.baselib.inter.OnItemClickListener;

import java.util.List;

/**
 * 创建者     CJR
 * 创建时间   2019/8/13 20:21
 * 描述       RecyclerView adapter的基类
 */
@SuppressWarnings({"unused"})
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    /**数据的集合*/
    protected List<T> mDataList;
    /**条目点击监听*/
    protected OnItemClickListener mOnItemClickListener;

    /**空参构造*/
    public BaseRvAdapter() {
    }

    /**
     * 构造函数
     * @param dataList 数据的集合
     */
    public BaseRvAdapter(List<T> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayoutResId(), parent, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    /**
     * 设置条目点击监听
     * @param listener 条目点击监听的listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 更新数据集合
     * @param dataList 数据集合
     */
    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }

    /**
     * 获取数据集合
     * @return 数据集合
     */
    public List<T> getDataList() {
        return mDataList;
    }

    /**
     * 获取条目的资源id
     * @return 条目的资源id
     */
    public abstract @LayoutRes
    int getItemLayoutResId();
}
