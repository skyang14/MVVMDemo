package com.yangshikun.mvvmdemo;

import android.util.Log;

import com.yangshikun.mvvmdemo.base.BaseActivity;
import com.yangshikun.mvvmdemo.databinding.ActivityMainBinding;
import com.yangshikun.mvvmdemo.utils.pinyin.HanyuPinyinHelper;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {
    private static final String TAG = "MainActivity";
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int viewModelId() {
        return BR.viewModel;
    }

    @Override
    protected void initData() {
        //liveData观察数据库数据变化并通知到页面
        AppDatabase.getDatabase().beanDao().queryAllLiveData().observe(this,beans -> {
            viewModel.items.clear();
            viewModel.items.addAll(beans);
        });
        //不观察，直接读取数据库
       // viewModel.items.addAll(AppDatabase.getDatabase().beanDao().queryAll());
        Log.e(TAG, "initData: "+ HanyuPinyinHelper.getPingYin("我爱中国"));
    }

    @Override
    protected void initView() {
        viewModel.getClickEvent().observe(this,o->{
            dataBinding.tvText.setText("文字变啦");
        });
    }
}
