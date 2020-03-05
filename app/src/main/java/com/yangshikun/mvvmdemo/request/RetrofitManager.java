package com.yangshikun.mvvmdemo.request;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yang.shikun on 2020/3/5 13:33
 */

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";
    private static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    private final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    protected static RetrofitManager getInstance() {
        return RetrofitHolder.retrofitManagement;
    }

    private static class RetrofitHolder {
        private static final RetrofitManager retrofitManagement = new RetrofitManager();
    }


    private RetrofitManager() {
    }

    private <T> T createRetrofit(Class<T> clz) {
        return createRetrofit(clz, Urls.BASE_URL);
    }

    private <T> T createRetrofit(Class<T> clz, String baseUrl) {
        //添加请求头
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, "RetrofitLog---->" + message);

            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clz);
    }

    class HeaderInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();

            Request build = request.newBuilder().addHeader("Content-Type", "application/json").build();
            return chain.proceed(build);
        }
    }


    public ApiService getService() {
        return getService(ApiService.class);
    }

    public <T> T getService(Class<T> clz) {
        T t;
        if(!serviceMap.containsKey(clz)) {
            t = createRetrofit(clz);
            serviceMap.put(clz.getSimpleName(), t);
        } else {
            t = (T) serviceMap.get(clz.getSimpleName());
        }
        return t;
    }
}
