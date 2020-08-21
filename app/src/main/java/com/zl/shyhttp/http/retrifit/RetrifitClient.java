package com.zl.shyhttp.http.retrifit;

import android.content.Context;
import android.os.Build;

import com.zl.shyhttp.http.interceptor.CacheRequestInterceptor;
import com.zl.shyhttp.http.interceptor.CacheResponseInterceptor;
import com.zl.shyhttp.http.interceptor.RequestToJsonInterceptor;

import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrifitClient {

    private static RetrifitClient mInstance;
    private Context mContext;
    private boolean isJson = false;
    private String mBaseUrl;

    private RetrifitClient() {

    }

    public static RetrifitClient getInstance() {

        if (mInstance == null) {
            synchronized (RetrifitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrifitClient();
                }
            }
        }
        return mInstance;
    }

    public RetrifitClient init(Context context) {
        this.mContext = context.getApplicationContext();
        return this;
    }

    public RetrifitClient baseUrl(String url) {
        this.mBaseUrl = url;
        return this;
    }

    public RetrifitClient parameterType(boolean isJson) {

        this.isJson = isJson;
        return this;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ApiService initApiService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();

        return retrofit.create(ApiService.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient;
        if (isJson) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10000L, TimeUnit.SECONDS)
                    .writeTimeout(10000L, TimeUnit.SECONDS)
                    .addInterceptor(new RequestToJsonInterceptor(mContext))
                    .addInterceptor(new CacheRequestInterceptor(mContext))
                    .addNetworkInterceptor(new CacheResponseInterceptor())
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10000L, TimeUnit.SECONDS)
                    .writeTimeout(10000L, TimeUnit.SECONDS)
                    .addInterceptor(new CacheRequestInterceptor(mContext))
                    .addNetworkInterceptor(new CacheResponseInterceptor())
                    .build();
        }
        return okHttpClient;
    }
}
