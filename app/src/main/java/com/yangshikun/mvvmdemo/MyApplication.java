package com.yangshikun.mvvmdemo;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.yangshikun.mvvmdemo.utils.pinyin.HanyuPinyinProperties;

/**
 * Created by yang.shikun on 2020/4/2 9:51
 */

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        HanyuPinyinProperties.init();
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance("MVVM"));
    }
}
