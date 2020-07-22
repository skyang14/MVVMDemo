package com.yangshikun.mvvmdemo.fragment.main;

import android.util.Log;

import com.yangshikun.mvvmdemo.AppDatabase;
import com.yangshikun.mvvmdemo.R;
import com.yangshikun.mvvmdemo.base.BaseFragment;
import com.yangshikun.mvvmdemo.databinding.FragmentMainBinding;
import com.yangshikun.mvvmdemo.fragment.kid.KidFragment;
import com.yangshikun.mvvmdemo.utils.pinyin.HanyuPinyinHelper;

import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Created by yang.shikun on 2020/7/22 14:05
 */

public class MainFragment extends BaseFragment<FragmentMainBinding,MainFragViewModel> {
    private static final String TAG = "MainFragment";
    @Override
    protected int layoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected int viewModelId() {
        return BR.viewModel;
    }

    @Override
    protected void initData() {
        //liveData观察数据库数据变化并通知到页面
        AppDatabase.getDatabase().beanDao().queryAllLiveData().observe(this, beans -> {
            viewModel.items.clear();
            viewModel.items.addAll(beans);
        });
        //不观察，直接读取数据库
        // viewModel.items.addAll(AppDatabase.getDatabase().beanDao().queryAll());
        Log.e(TAG, "initData: "+ HanyuPinyinHelper.getPingYin("我爱中国"));
    }

    @Override
    protected void initView() {
        viewModel.getClickEvent().observe(this, o -> {
            start(new KidFragment());
        });
    }

    @Override
    protected void onLazyInit() {

    }

    @Override
    protected boolean disableBackground() {
        return false;
    }
}
