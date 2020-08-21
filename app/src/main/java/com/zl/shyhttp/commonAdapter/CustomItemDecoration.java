package com.zl.shyhttp.commonAdapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by zhangli on 2019/4/19.
 */

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivier;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int bottom = mDivier
        if (isLastCloun(view, parent)) {

        } else if (isLastRow(view, parent)) {

        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


    /**
     * 最后一行
     */
    private boolean isLastRow(View view, RecyclerView parent) {

        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        return (position + 1) % spanCount == 0;
    }

    /**
     * 最后一列
     */
    private boolean isLastCloun(View view, RecyclerView parent) {
        int position = parent.getChildLayoutPosition(view);
        int spanCount = getSpanCount(parent);
        int itemcount = parent.getAdapter().getItemCount();
        int rowCount = itemcount / spanCount;

        return (rowCount - 1) * spanCount < (position + 1);
    }

    /**
     * 获取Recycler 列数
     */
    private int getSpanCount(RecyclerView parent) {

        RecyclerView.LayoutManager layoutmanager = parent.getLayoutManager();
        if (layoutmanager instanceof GridLayoutManager) {
            int spanCount = ((GridLayoutManager) layoutmanager).getSpanCount();
            return spanCount;
        }
        return 1;
    }
}
