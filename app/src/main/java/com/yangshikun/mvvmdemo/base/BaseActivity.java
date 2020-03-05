package com.yangshikun.mvvmdemo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.liam.support.SupportActivity;

/**
 * Created by yang.shikun on 2020/3/5 13:30
 */

public abstract class BaseActivity<T extends ViewDataBinding, VM extends BaseViewModel> extends SupportActivity {
    private String TAG = getClass().getSimpleName();

    protected T dataBinding;

    protected VM viewModel;

    private boolean needRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, layoutId());
        initViewModel();
        initData();
        initView();
        viewModel.getFinishEvent().observe(this, o -> finish());
        needRegister = needRegisterEventBus(getClass());
        if (needRegister) {
            EventBus.getDefault().register(this);
        }
    }

    private boolean needRegisterEventBus(Class clazz) {
        needRegister = false;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                return true;
            }
        }
        if (clazz.getSuperclass() != null) {
            return needRegisterEventBus(clazz.getSuperclass());
        }
        return false;
    }

    private void initViewModel() {
        int viewModelId = viewModelId();
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class vmClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            viewModel = (VM) new ViewModelProvider(this).get(vmClass);
        }
        dataBinding.setVariable(viewModelId, viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (needRegister) {
            EventBus.getDefault().unregister(this);
        }
    }

    public int getNavBarHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    private int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    protected abstract int layoutId();

    protected abstract int viewModelId();

    protected abstract void initData();

    protected abstract void initView();
}

