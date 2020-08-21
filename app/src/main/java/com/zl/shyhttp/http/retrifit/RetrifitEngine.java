package com.zl.shyhttp.http.retrifit;

import android.annotation.SuppressLint;
import android.content.Context;


import com.zl.shyhttp.http.EngineCallback;
import com.zl.shyhttp.http.IHttpEngine;

import java.util.Map;

/**
 * Created by ZhangL on 2020-06-22.
 */
public class RetrifitEngine implements IHttpEngine {
    @SuppressLint("NewApi")
    @Override
    public void get(Context context, String url, Map<String, Object> params, final EngineCallback callback) {
        if (params == null) {
            ApiSubcribe.getInstance().GetMethod(url, new RealObserver() {
                @Override
                protected void onSuccess(String value) {
                    callback.onSuccess(value);
                }

                @Override
                protected void onFailer(int code, String msg) {
                    callback.onFailure(code, msg);
                }
            });
        } else {
            ApiSubcribe.getInstance().GetMethod(url, params, new RealObserver() {
                @Override
                protected void onSuccess(String value) {
                    callback.onSuccess(value);
                }

                @Override
                protected void onFailer(int code, String msg) {
                    callback.onFailure(code, msg);
                }
            });
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void post(Context context, String url, Map<String, Object> params, final EngineCallback callback) {
        ApiSubcribe.getInstance().PostMethod(url, params, new RealObserver() {
            @Override
            protected void onSuccess(String value) {
                callback.onSuccess(value);
            }

            @Override
            protected void onFailer(int code, String msg) {
                callback.onFailure(code, msg);
            }
        });
    }

    @Override
    public void download(Context context, String url, Map<String, Object> params, EngineCallback callback) {

    }

    @Override
    public void upload(Context context, String url, Map<String, Object> params, EngineCallback callback) {

    }
}
