package com.yangshikun.mvvmdemo.request;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by yang.shikun on 2020/3/5 13:48
 */

public class MySubscriber<T> extends DisposableObserver<T> {

    private final Requester.Listener<T> listener;

    public MySubscriber(Requester.Listener<T> listener) {
        this.listener = listener;
    }

    @Override
    protected void onStart() {
        listener.onStart();
    }

    @Override
    public void onNext(T o) {
        listener.onSucceed(o);
    }

    @Override
    public void onError(Throwable e) {
        listener.onFaild(e);
    }

    @Override
    public void onComplete() {
        listener.onComplete();
    }

}

