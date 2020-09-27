package com.chaomeng.baselib.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;


/**
 * 创建者     CJR
 * 创建时间   2017-08-02 10:48
 * 描述	     万能分割线, 只保留了竖直方向的处理
 * 参考网址   http://blog.csdn.net/pengkv/article/details/50538121
 */
@SuppressWarnings("unused")
public class RvItemDecoration extends RecyclerView.ItemDecoration {

    /**绘制分割线的画笔*/
    private Paint mPaint;
    /**分割线颜色, 默认为#bdbdbd*/
    private int mDividerColor = 0xFFBDBDBD;
    /**分割线高度，默认为1px*/
    private int mDividerHeight = 1;
    /**分割线到左边的距离, 默认为0*/
    private float mMarginLeft;
    /**分割线到右边的距离, 默认为0*/
    private float mMarginRight;
    /**最后一条分割线是否绘制, 默认不绘制*/
    private boolean mIsDrawLastLine;

    /**
     * 空参构造
     */
    public RvItemDecoration() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    /**
     * 获取分割线尺寸
     *
     * 参考: https://www.jianshu.com/p/26b33e1181e3
     */
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view,
                               @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mIsDrawLastLine) {
            //  最后一个条目也画分割线
            outRect.set(0, 0, 0, mDividerHeight);
            return;
        }

        //  最后一个条目不画分割线, 获取最后一个条目数
        final int lastPosition = state.getItemCount() - 1;
        //  获取当前位置
        int curPosition = parent.getChildAdapterPosition(view);
        if (curPosition < lastPosition) {
            //  不是最后一个条目
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            //  最后一个条目
            outRect.set(0, 0, 0, 0);
        }
    }

    /**
     * 绘制方法
     */
    @Override
    public void onDraw(@NotNull Canvas c, @NotNull RecyclerView parent,
                       @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent, state);
    }

    /**
     * RecyclerView是vertical方向的, 绘制 item 分割线(一条横线)
     *
     * @param canvas 画布
     * @param parent RecyclerView实例
     * @param state  RecyclerView的状态
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (mPaint == null) {
            return;
        }
        final float left = parent.getPaddingLeft() + mMarginLeft;
        final float right = parent.getMeasuredWidth() - parent.getPaddingRight() - mMarginRight;

        //  界面上展示的条目的数量
        final int childCount = parent.getChildCount();
        //  当前可见的条目中最后条目的位置
        final int lastChildPosition = parent.getChildAdapterPosition(
                parent.getChildAt(childCount - 1));

        //  最后条目的position 就是adapter的getItemCount中最后那个, 和见面上最后可见的有区别
        final int lastItemPosition = state.getItemCount() - 1;

        //  绘制的分割线的数量
        int drawCount = parent.getChildCount();
        if (!mIsDrawLastLine && lastChildPosition == lastItemPosition) {
            //  不画最后一条分割线, 并且 当前可见的最后条目 是列表(adapter的getItemCount)的最后条目
            drawCount -= 1;
        }

        for (int i = 0; i < drawCount; i++) {
            final View child = parent.getChildAt(i);
            drawLine(canvas, child, left, right);
        }
    }

    /**
     * 绘制分割线的线条
     *
     * @param canvas 画布
     * @param child  要绘制分割线的条目的itemView
     * @param left   左边界
     * @param right  右边界
     */
    private void drawLine(Canvas canvas, View child, float left, float right) {
        RecyclerView.LayoutParams layoutParams =
                (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getBottom() + layoutParams.bottomMargin;
        final int bottom = top + mDividerHeight;
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 获取分割线颜色
     *
     * @return 分割线颜色
     */
    public int getDividerColor() {
        return mDividerColor;
    }

    /**
     * 设置分割线颜色
     *
     * @param dividerColor 分割线颜色
     * @return 当前对对象, 形成链式调用
     */
    public RvItemDecoration setDividerColor(int dividerColor) {
        mDividerColor = dividerColor;
        if (mPaint != null) {
            mPaint.setColor(dividerColor);
        }
        return this;
    }

    /**
     * 获取分割线高度
     *
     * @return 分割线高度
     */
    public int getDividerHeight() {
        return mDividerHeight;
    }

    /**
     * 设置分割线高度
     *
     * @param dividerHeight 分割线高度
     * @return 当前对对象, 形成链式调用
     */
    public RvItemDecoration setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
        return this;
    }

    /**
     * 获取分割线到左边的距离
     *
     * @return 分割线到左边的距离
     */
    public float getMarginLeft() {
        return mMarginLeft;
    }

    /**
     * 设置分割线到左边的距离
     *
     * @param marginLeft 分割线到左边的距离
     * @return 当前对对象, 形成链式调用
     */
    public RvItemDecoration setMarginLeft(float marginLeft) {
        mMarginLeft = marginLeft;
        return this;
    }

    /**
     * 获取分割线到右边的距离
     *
     * @return 分割线到右边的距离
     */
    public float getMarginRight() {
        return mMarginRight;
    }

    /**
     * 设置分割线到右边的距离
     *
     * @param marginRight 分割线到右边的距离
     * @return 当前对象, 形成链式调用
     */
    public RvItemDecoration setMarginRight(float marginRight) {
        mMarginRight = marginRight;
        return this;
    }

    /**
     * 获取最后一条分割线是否绘制
     *
     * @return 最后一条分割线是否绘制
     */
    public boolean isDrawLastLine() {
        return mIsDrawLastLine;
    }

    /**
     * 设置最后一条分割线是否绘制
     *
     * @param drawLastLine 最后一条分割线是否绘制
     * @return 当前对对象, 形成链式调用
     */
    public RvItemDecoration setDrawLastLine(boolean drawLastLine) {
        mIsDrawLastLine = drawLastLine;
        return this;
    }
}