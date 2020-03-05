package com.yangshikun.mvvmdemo.request;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yang.shikun on 2020/3/5 13:34
 */

public interface ApiService {
    @POST("{path}")
    Observable<JsonObject> getJson(@Path(value = "path", encoded = true) String path,
                                   @HeaderMap Map<String, String> headers, @Body RequestBody requestBody);
}
