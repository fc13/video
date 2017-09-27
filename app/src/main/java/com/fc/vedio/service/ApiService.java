package com.fc.vedio.service;

import com.fc.vedio.base.BaseModel;
import com.fc.vedio.entity.ApkDesc;
import com.fc.vedio.entity.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * @author 范超 on 2017/9/7
 */

public interface ApiService {
    //获得版本号
    @GET("apkFile")
    Observable<BaseModel<ApkDesc>> getVersion();

    //下载更新
    @Streaming
    @GET("download")
    Observable<ResponseBody> download(@Query("url") String url);

    //登录
    @GET("login")
    Observable<BaseModel<String>> login(@Query("name") String userName,
                                        @Query("password") String password);

    //注册
    @POST("people")
    Observable<BaseModel<User>> register(@Body User user);
}
