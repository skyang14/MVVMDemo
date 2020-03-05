package com.yangshikun.mvvmdemo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import me.liam.support.SupportFragment;

/**
 * Created by yang.shikun on 2020/3/5 14:14
 */

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends SupportFragment {
    private String TAG = getClass().getSimpleName();
    public V dataBinding;

    public VM viewModel;

    private boolean needRegister = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            setArguments(new Bundle());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        initDataBinding();
        needRegister = needRegisterEventBus(getClass());
        if (needRegister) {
            EventBus.getDefault().register(this);
        }
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (disableBackground()) {
            if (getView() != null) {
                getView().setBackground(null);
            }
        }
        initData();
        initView();
        viewModel.getFinishEvent().observe(getViewLifecycleOwner(), o -> pop());
    }

    @Override
    public void onLazyInit(@Nullable Bundle savedInstanceState) {
        super.onLazyInit(savedInstanceState);
        viewModel.onLazyInit();
        onLazyInit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (needRegister) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void initDataBinding() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            Class aClass = (Class) types[1];
            viewModel = (VM) new ViewModelProvider(this).get(aClass);
        }

        dataBinding.setVariable(viewModelId(), viewModel);
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

    /**
     * fragment布局id
     *
     * @return
     */
    protected abstract int layoutId();

    /**
     * fragment布局viewModel所设置的id（一般设置为BR.viewModel）
     *
     * @return
     */
    protected abstract int viewModelId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 懒加载（fragment对用户可见时）
     */
    protected abstract void onLazyInit();

    /**
     * 若无需fragment背景，复写为true（默认false）
     *
     * @return
     */
    protected abstract boolean disableBackground();
}

