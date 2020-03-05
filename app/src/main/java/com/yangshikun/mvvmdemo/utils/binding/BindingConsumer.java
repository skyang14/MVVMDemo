package com.yangshikun.mvvmdemo.utils.binding;

/**
 * Created by yang.shikun on 2020/3/5 13:33
 */

public interface BindingConsumer<T> {
    void call(T t);
}

