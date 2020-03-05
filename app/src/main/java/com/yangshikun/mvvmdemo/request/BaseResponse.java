package com.yangshikun.mvvmdemo.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yang.shikun on 2020/3/5 13:39
 */

public class BaseResponse<T> {

    @SerializedName("code")
    int code;

    @SerializedName("msg")
    String reson;

    @SerializedName("data")
    T t;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

}
