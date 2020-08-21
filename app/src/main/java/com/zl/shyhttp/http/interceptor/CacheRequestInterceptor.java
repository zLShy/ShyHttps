package com.zl.shyhttp.http.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;


import com.zl.shyhttp.http.retrifit.LocalNetWorkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheRequestInterceptor implements Interceptor {

    private Context mContext;

    public CacheRequestInterceptor(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        if (!LocalNetWorkUtils.isNetConnected(mContext)) {
            request = new Request.Builder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        SharedPreferences preferences = mContext.getSharedPreferences("share_data", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        if (TextUtils.isEmpty(token)) {
            request = request.newBuilder()
                    .build();
        } else {
            request = request.newBuilder()
                    .addHeader("TOKEN", preferences.getString("token", null))
                    .build();
        }

        return chain.proceed(request);
    }
}
