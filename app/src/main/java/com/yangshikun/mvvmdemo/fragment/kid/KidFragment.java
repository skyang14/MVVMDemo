package com.yangshikun.mvvmdemo.fragment.kid;

import com.yangshikun.mvvmdemo.R;
import com.yangshikun.mvvmdemo.base.BaseFragment;
import com.yangshikun.mvvmdemo.databinding.FragmentKidBinding;

import me.tatarka.bindingcollectionadapter2.BR;

/**
 * Created by yang.shikun on 2020/7/22 14:06
 */

public class KidFragment extends BaseFragment<FragmentKidBinding,KidViewModel> {
    @Override
    protected int layoutId() {
        return R.layout.fragment_kid;
    }

    @Override
    protected int viewModelId() {
        return BR.viewModel;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onLazyInit() {

    }

    @Override
    protected boolean disableBackground() {
        return false;
    }
}
