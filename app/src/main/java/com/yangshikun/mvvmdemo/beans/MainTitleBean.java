package com.yangshikun.mvvmdemo.beans;

import android.graphics.drawable.Drawable;

/**
 * Created by yang.shikun on 2020/7/23 10:19
 */

public class MainTitleBean {
    private Drawable drawable;
    private String title;
    //是否在栈顶
    private boolean isTopStack;

    public MainTitleBean(Drawable drawable, String title, boolean isTopStack) {
        this.drawable = drawable;
        this.title = title;
        this.isTopStack = isTopStack;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isTopStack() {
        return isTopStack;
    }

    public void setTopStack(boolean topStack) {
        isTopStack = topStack;
    }
}
