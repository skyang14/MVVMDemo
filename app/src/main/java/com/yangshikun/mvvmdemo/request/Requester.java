package com.yangshikun.mvvmdemo.request;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by yang.shikun on 2020/3/5 13:33
 */

public class Requester {
    private static final String TAG = "Requester";
    private static Gson sGson;

    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private static Application sContext;

    public static void init(Application context) {
        sContext = context;
        sGson = new Gson();
    }

    public static void getJson(String url, Map<String, Object> map, Listener<JsonObject> listener) {
        getJson(url, map, null, listener);
    }

    public static void getJson(String url, Map<String, Object> map, Map<String, String> header,
                               Listener<JsonObject> listener) {
        if (header == null) {
            header = new HashMap<>();
            map.put("Content-Type", "application/json");
        }
        RequestBody requestBody = map2Json(map);
        getJson(url, requestBody, header, listener);
    }

    public static void getJson(String url, RequestBody requestBody, Map<String, String> header, Listener<JsonObject> listener) {
        if (header == null) {
            header = new HashMap<>();
        }
        MySubscriber<JsonObject> observer = new MySubscriber<>(listener);
        Observable<JsonObject> observable = RetrofitManager
                .getInstance()
                .getService()
                .getJson(url, header, requestBody);
        observable
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                //调用unsubscribeOn后,之前的RxJava消亡
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static <T> void getBean(String url, Map<String, Object> map, Listener<T> listener) {
        getBean(url, map, null, listener);
    }

    public static <T> void getBean(String url, Map<String, Object> map, Map<String, String> header, Listener<T> listener) {
        if (header == null) {
            header = new HashMap<>();
            map.put("Content-Type", "application/json");
        }
        RequestBody requestBody = map2Json(map);
        getBean(url, requestBody, header, listener);
    }

    public static <T> void getBean(String url, RequestBody body, Listener<T> listener) {
        getBean(url, body, null, listener);
    }

    private static <T> void getBean(String url, RequestBody body, Map<String, String> header, Listener<T> listener) {
        getJson(url, body, header, new ListenerAdapter<JsonObject>() {
            @Override
            public void onSucceed(JsonObject object) {
                super.onSucceed(object);
                try {
                    Log.e(TAG, "NetWorker --> onSucceed: " + object);
                    Type argument;
                    if (listener.getClass().isInterface()) {
                        argument = ((ParameterizedType) listener.getClass().getGenericInterfaces()[0])
                                .getActualTypeArguments()[0];
                    } else {
                        argument = ((ParameterizedType) listener.getClass().getGenericSuperclass())
                                .getActualTypeArguments()[0];
                    }
                    BaseResponse<T> baseResponse = json2Data(object, argument);
                    if (baseResponse.code == ResponseCodes.CODE_SUCCESS) {
                        T bean = baseResponse.getT();
                        listener.onSucceed(bean);
                    } else {
                        listener.onFaild(new Throwable(baseResponse.reson));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFaild(e);
                }
            }

            @Override
            public void onFaild(Throwable throwable) {
                super.onFaild(throwable);
                listener.onFaild(throwable);
            }
        });
    }

    public static <T> BaseResponse<T> json2Data(JsonObject object, Type arg) {
        Type type = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{arg});
        return sGson.fromJson(object, type);
    }

    private static RequestBody map2Json(Map map) {
        return RequestBody.create(MEDIA_TYPE_JSON, sGson.toJson(map));
    }

    public interface Listener<T> {

        /**
         * 请求发起时
         */
        void onStart();

        /**
         * @param t 成功返回
         */
        void onSucceed(T t);

        void onFaild(Throwable throwable);

        void onComplete();
    }

}

