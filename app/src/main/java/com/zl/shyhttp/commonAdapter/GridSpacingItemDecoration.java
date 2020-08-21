package com.zl.shyhttp.commonAdapter;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

   private int spanCount; //列数
   private int spacing; //间隔
   private Context mContext; //是否包含边缘

    public GridSpacingItemDecoration(int spanCount, int spacing, Context context) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.mContext = context.getApplicationContext();

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        // 获取item在span中的下标
        int spanIndex = params.getSpanIndex();
        int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.spacing, mContext.getResources().getDisplayMetrics());
        // 中间间隔
        if (spanIndex % 2 == 0) {
            outRect.left = 0;
        } else {
            // item为奇数位，设置其左间隔为5dp
            outRect.left = interval;
        }
        // 下方间隔
        outRect.bottom = interval;

        Log.e("TAG","Rect-->t,b,l,f"+outRect.top+"-"+outRect.bottom+"-"+outRect.left+"-"+outRect.right+"-position"+parent.getChildAdapterPosition(view));
    }

}