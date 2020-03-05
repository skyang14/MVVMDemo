package com.yangshikun.mvvmdemo.request;

/**
 * Created by yang.shikun on 2020/3/5 13:34
 */

public class ListenerAdapter<T> implements Requester.Listener<T> {
    @Override
    public void onStart() {

    }

    @Override
    public void onSucceed(T t) {

    }

    @Override
    public void onFaild(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}

