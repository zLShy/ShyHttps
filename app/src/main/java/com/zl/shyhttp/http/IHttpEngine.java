package com.zl.shyhttp.http;

import android.content.Context;

import java.util.Map;

public interface IHttpEngine {
    void get(Context context, String url, Map<String, Object> params,
             final EngineCallback callback);

    void post(Context context, String url, Map<String, Object> params,
              final EngineCallback callback);

    void download(Context context, String url, Map<String, Object> params,
                  final EngineCallback callback);

    void upload(Context context, String url, Map<String, Object> params,
                final EngineCallback callback);
}
