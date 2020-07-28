package com.yangshikun.mvvmdemo.utils;

/**
 *
 * 不依赖于生命周期观察者的事件监听回调
 * 相对于SingleLiveEvent用起来更普遍适用，用法跟SingleLiveEvent差不多
 * 只是少传一个生命周期观察者参数
 */
public class ActionObserver<T> {
    private Listener listener;

    public void observe(Listener<T> listener) {
        this.listener = listener;
    }

    public void active() {
        if (listener != null) {
            listener.onDoing(null);
        }
    }

    public void setValue(T t) {
        if (listener != null) {
            listener.onDoing(t);
        }
    }

    public interface Listener<T> {
        void onDoing(T value);
    }
}
