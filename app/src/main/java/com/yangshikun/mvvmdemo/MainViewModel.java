package com.yangshikun.mvvmdemo;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.yangshikun.mvvmdemo.base.BaseViewModel;
import com.yangshikun.mvvmdemo.beans.Bean;
import com.yangshikun.mvvmdemo.daos.BeanDao;
import com.yangshikun.mvvmdemo.utils.AppExecutors;
import com.yangshikun.mvvmdemo.utils.binding.BindingAction;
import com.yangshikun.mvvmdemo.utils.binding.BindingCommand;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by yang.shikun on 2020/3/5 13:23
 */

public class MainViewModel extends BaseViewModel {
    public ItemBinding<Bean> itemBinding;
    //可观察的列表，类似的还有ObservableBoolean、 ObservableInt等
    public ObservableList<Bean> items;
    private SingleLiveEvent clickEvent;
    private BeanDao beanDao = AppDatabase.getDatabase().beanDao();
    @Override
    protected void onCreate() {
        //绑定item到recyclerView
        itemBinding = ItemBinding.of(BR.bean, R.layout.item_recyclerview);
        //绑定viewModel到recyclerView
        itemBinding.bindExtra(BR.viewModel, this);
        //recyclerView列表
        items = new ObservableArrayList<>();
        clickEvent = new SingleLiveEvent();
    }

    @Override
    protected void onDestroy() {

    }

    public SingleLiveEvent getClickEvent() {
        return clickEvent;
    }

    public BindingCommand clickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            clickEvent.call();
        }
    });

    public BindingCommand recyclerViewCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            items.clear();
            List<Bean> beanList = new ArrayList<>();
            beanList.add(new Bean("大家好"));
            beanList.add(new Bean("我叫"));
            beanList.add(new Bean("VAE"));
            beanList.add(new Bean("这是我的"));
            beanList.add(new Bean("首张独唱专辑"));
            beanList.add(new Bean("自定义"));
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                   beanDao.insertAll(beanList);
                }
            });
        }
    });

    public BindingCommand deleteCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    beanDao.deleteAll();
                }
            });
        }
    });
}
