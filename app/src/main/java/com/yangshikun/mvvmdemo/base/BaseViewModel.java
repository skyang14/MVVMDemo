package com.yangshikun.mvvmdemo.base;

import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yangshikun.mvvmdemo.SingleLiveEvent;
import com.yangshikun.mvvmdemo.request.BaseResponse;
import com.yangshikun.mvvmdemo.request.ListenerAdapter;
import com.yangshikun.mvvmdemo.request.Requester;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.RequestBody;

/**
 * Created by yang.shikun on 2020/3/5 13:31
 */

public abstract class BaseViewModel extends ViewModel {
    protected String TAG = getClass().getSimpleName();
    private CompositeDisposable disposable;
    private Gson mGson;
    private CompositeDisposable compositeDisposable;
    private SingleLiveEvent finishEvent;

    public BaseViewModel() {
        finishEvent = new SingleLiveEvent();
        onCreate();
    }

    @Override
    public void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
        onDestroy();
    }

    public void out() {
        finishEvent.call();
    }

    SingleLiveEvent getFinishEvent() {
        return finishEvent;
    }

    /**
     * 带请求头请求
     *
     * @param url      地址
     * @param map      params
     * @param header   请求头(null if without headers)
     * @param callback 回调
     * @param <T>      返回类型
     */
    protected <T> void request(String url, Map<String, Object> map, Map<String, String> header, RequestCallback<T> callback) {
        if (mGson == null) {
            mGson = new Gson();
        }
        map.put("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(Requester.MEDIA_TYPE_JSON, mGson.toJson(map));
        request(url, requestBody, header, callback);
    }

    /**
     * 带请求头网络请求
     *
     * @param url      地址
     * @param body     请求体
     * @param header   请求头(null if without headers)
     * @param callback 回调
     * @param <T>      具体返回bean类型
     */
    protected <T> void request(String url, RequestBody body, Map<String, String> header, RequestCallback<T> callback) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) {

                try {
                    Requester.getJson(url, body, header, new ListenerAdapter<JsonObject>() {
                        @Override
                        public void onSucceed(JsonObject object) {
                            super.onSucceed(object);
                            try {
                                Type type = ((ParameterizedType) callback.getClass().getGenericInterfaces()[0])
                                        .getActualTypeArguments()[0];
                                BaseResponse<T> response = Requester.json2Data(object, type);
                                if (response.getCode() == 0) {
                                    T bean = response.getT();
                                    if (!emitter.isDisposed()) {
                                        emitter.onNext(bean);
                                    }
                                } else {
                                    if (!emitter.isDisposed()) {
                                        emitter.onError(new Throwable(response.getReson()));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (!emitter.isDisposed()) {
                                    emitter.onError(e);
                                }
                            }
                        }

                        @Override
                        public void onFaild(Throwable throwable) {
                            super.onFaild(throwable);
                            if (!emitter.isDisposed()) {
                                emitter.onError(throwable);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }

            }
        }).subscribe(callback::onSucceed, callback::onFailed));
    }

    protected abstract void onCreate();

    protected abstract void onDestroy();

    //只在fragment中起作用
    public void onLazyInit() {

    }


    protected interface RequestCallback<T> {
        void onSucceed(T t);

        void onFailed(Throwable throwable);
    }
}

