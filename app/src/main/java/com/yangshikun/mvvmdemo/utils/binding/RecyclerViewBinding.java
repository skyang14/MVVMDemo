package com.yangshikun.mvvmdemo.utils.binding;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * xml中配置RecyclerView
 *
 * @author zytt
 */
public class RecyclerViewBinding {

    @BindingAdapter(value = {"spanSize", "gaph", "gapv"}, requireAll = false)
    public static void setRecyclerView(RecyclerView recyclerView, int spanSize, int gaph, int gapv) {
        RecyclerView.LayoutManager layout;
        int gapH = gaph == 0 ? 0 : (int) recyclerView.getResources().getDimension(gaph);
        int gapV = gapv == 0 ? 0 : (int) recyclerView.getResources().getDimension(gapv);
        if (spanSize == 0) {
            layout = new LinearLayoutManager(recyclerView.getContext());
            recyclerView.addItemDecoration(new MyItemDecoration(gapH, gapV));
        } else {
            layout = new GridLayoutManager(recyclerView.getContext(), spanSize);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanSize, gapH, gapV, false));
        }
        recyclerView.setLayoutManager(layout);
//        new LinearSnapHelper().attachToRecyclerView(recyclerView);

    }

    private static class MyItemDecoration extends RecyclerView.ItemDecoration {

        /**
         * item横向和垂直方向间距
         */
        private final int gapH;
        private final int gapV;

        MyItemDecoration(int gapH, int gapV) {
            this.gapH = gapH;
            this.gapV = gapV;
            Log.d("MyItemDecoration", "gapH:" + gapH);
            Log.d("MyItemDecoration", "gapV:" + gapV);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = gapV / 2;
            outRect.top = gapV / 2;
            outRect.left = gapH / 2;
            outRect.right = gapH / 2;

        }
    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount; //列数
        private int spacingH; //横向间隔
        private int spacingV; //纵向间隔
        private boolean includeEdge; //是否包含边缘

        public GridSpacingItemDecoration(int spanCount, int spacingH, int spacingV, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacingH = spacingH;
            this.spacingV = spacingV;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //这里是关键，需要根据你有几列来判断
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacingH - column * spacingH / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacingH / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacingV;
                }
                outRect.bottom = spacingV; // item bottom
            } else {
                outRect.left = column * spacingH / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacingH - (column + 1) * spacingH / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacingV; // item top
                }
            }
        }
    }

    /**
     * @param recyclerView
     * @param orientation  0水平 1竖直
     */
    @BindingAdapter(value = {"linearOri", "scrollToCenter"}, requireAll = false)
    public static void setOrientation(RecyclerView recyclerView, int orientation, boolean scrollToCenter) {
        LinearLayoutManager manager;
        if (scrollToCenter) {
            manager = new CenterLinearManagerLayout(recyclerView.getContext());
        } else {
            manager = new LinearLayoutManager(recyclerView.getContext());
        }
        manager.setOrientation(orientation);
        recyclerView.setLayoutManager(manager);
    }

}
