package com.chaomeng.baselib.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     CJR
 * 创建时间   2019/8/13 19:47
 * 描述       鸿洋大神的 来源: https://github.com/hongyangAndroid/baseAdapter
 */
public class HeaderAndFooterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**header条目的起始位置*/
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    /**footer条目的起始位置*/
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    /**存储Header条目的集合*/
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    /**存储头部条目的集合*/
    private ArrayList<View> mFootViews = new ArrayList<>();
    /**包装的adapter*/
    private RecyclerView.Adapter mInnerAdapter;

    /**
     * 构造方法
     * @param adapter 包装的Adapter
     */
    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    @NonNull
    @SuppressWarnings("ConstantConditions")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType >= BASE_ITEM_TYPE_FOOTER) {
            //  footer类型
            return new RecyclerView.ViewHolder(mFootViews.get(viewType - BASE_ITEM_TYPE_FOOTER)) {};
        }
        if (viewType >= BASE_ITEM_TYPE_HEADER) {
            //  header类型
            return new RecyclerView.ViewHolder(mHeaderViews.get(viewType - BASE_ITEM_TYPE_HEADER)) {};
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return position + BASE_ITEM_TYPE_HEADER;
        } else if (isFooterViewPos(position)) {
            return position - getHeadersCount() - getRealItemCount() + BASE_ITEM_TYPE_FOOTER;
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    /**获取真实的条目数量*/
    public int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position
            , @NonNull List<Object> payloads) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount(), payloads);
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager
                    .getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    } else if (mFootViews.get(viewType) != null) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager
                        .LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 判断某一位置是否header
     * @param position 位置
     * @return 是否header
     */
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断某一位置是否footer
     * @param position 位置
     * @return 是否footer
     */
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    /**
     * 添加header
     * @param view header的contentView
     */
    public void addHeaderView(View view) {
        mHeaderViews.add(view);
    }

    /**
     * 添加footer
     * @param view footer的contentView
     */
    public void addFootView(View view) {
        mFootViews.add(view);
    }

    /**获取header的数量*/
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**获取footer的数量*/
    public int getFootersCount() {
        return mFootViews.size();
    }


}
