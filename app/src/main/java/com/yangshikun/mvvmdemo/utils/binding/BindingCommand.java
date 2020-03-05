package com.yangshikun.mvvmdemo.utils.binding;

/**
 * Created by yang.shikun on 2020/3/5 13:32
 */

public class BindingCommand<T> {

    BindingConsumer<T> consumer;

    BindingAction action;

    public BindingCommand(BindingConsumer<T> consumer) {
        this.consumer = consumer;
    }

    public BindingCommand(BindingAction action) {
        this.action = action;
    }

    public void execute() {
        if(action == null) {
            return;
        }
        action.call();
    }

    public void execute(T t) {
        if(consumer == null) {
            return;
        }
        consumer.call(t);
    }

}

