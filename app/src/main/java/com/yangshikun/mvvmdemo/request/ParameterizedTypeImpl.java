package com.yangshikun.mvvmdemo.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by yang.shikun on 2020/3/5 13:40
 */

public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;

    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }

    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }

    @Override
    public Type getRawType() {
        return raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}

