package com.fc.vedio;


import com.fc.vedio.download.DownloadProgressInterceptor;
import com.fc.vedio.download.DownloadProgressListener;
import com.fc.vedio.service.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 范超 on 2017/9/7
 */

public class Api {
    private static ApiService SERVICE;
    private static ApiService DOWNLOAD_SERVICE;
    private static String BASE_URL = "http://192.168.191.1:8080/api/";

    public static ApiService getDefault() {
        if (SERVICE == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            SERVICE = retrofit.create(ApiService.class);
        }
        return SERVICE;
    }

    public static ApiService getDefault(DownloadProgressListener listener) {
        if (DOWNLOAD_SERVICE == null) {
            DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(interceptor)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            DOWNLOAD_SERVICE = retrofit.create(ApiService.class);
        }
        return DOWNLOAD_SERVICE;
    }
}
