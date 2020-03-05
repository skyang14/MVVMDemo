package com.yangshikun.mvvmdemo.utils.binding;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class CenterLinearManagerLayout extends LinearLayoutManager {
    private CenterSmoothScroller smoothScroller;

    public CenterLinearManagerLayout(Context context) {
        super(context);
        smoothScroller = new CenterSmoothScroller(context);
    }

    public CenterLinearManagerLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        smoothScroller = new CenterSmoothScroller(context);
    }

    public CenterLinearManagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        smoothScroller = new CenterSmoothScroller(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private static class CenterSmoothScroller extends LinearSmoothScroller {

        public CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 200f / displayMetrics.densityDpi;
        }
    }
}
