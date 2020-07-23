package com.yangshikun.mvvmdemo;

import android.content.Context;

import com.yangshikun.mvvmdemo.base.BaseViewModel;
import com.yangshikun.mvvmdemo.beans.MainTitleBean;
import com.yangshikun.mvvmdemo.utils.binding.BindingCommand;
import com.yangshikun.mvvmdemo.utils.binding.BindingConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by yang.shikun on 2020/3/5 13:23
 */

public class MainViewModel extends BaseViewModel {
    public ItemBinding<MainTitleBean> itemBinding;
    public List<MainTitleBean> items;
    private SingleLiveEvent<MainTitleBean> itemClickEvent;
    @Override
    protected void onCreate() {
        itemBinding = ItemBinding.of(BR.bean, R.layout.item_main_title);
//        绑定viewModel到recyclerView
        itemBinding.bindExtra(BR.viewModel, this);
//        recyclerView列表
        items = new ArrayList<>();
        itemClickEvent = new SingleLiveEvent<>();
        initTitle();
    }

    private void initTitle(){
        Context context = MyApplication.getContext();
        items.add(new MainTitleBean(context.getDrawable(R.drawable.ic_launcher_background),"我的账号",true));
        items.add(new MainTitleBean(context.getDrawable(R.drawable.ic_launcher_background),"账号切换",false));
        items.add(new MainTitleBean(context.getDrawable(R.drawable.ic_launcher_background),"数据同步",false));
        items.add(new MainTitleBean(context.getDrawable(R.drawable.ic_launcher_background),"授权管理",false));
    }
    public SingleLiveEvent<MainTitleBean> getItemClickEvent() {
        return itemClickEvent;
    }

    public BindingCommand<MainTitleBean> itemClick = new BindingCommand<>(new BindingConsumer<MainTitleBean>() {
        @Override
        public void call(MainTitleBean mainTitleBean) {
            int index = items.indexOf(mainTitleBean);
            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0,1,2,3));
            list.remove(index);
            items.get(index).setTopStack(true);
            for (Integer integer : list) {
                items.get(integer).setTopStack(false);
            }
            itemClickEvent.setValue(mainTitleBean);
        }
    });
    @Override
    protected void onDestroy() {

    }

}
