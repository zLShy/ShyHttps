package com.zl.shyhttp.http.okhttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.zl.shyhttp.http.EngineCallback;
import com.zl.shyhttp.http.HttpUtils;
import com.zl.shyhttp.http.IHttpEngine;
import com.zl.shyhttp.http.interceptor.CacheRequestInterceptor;
import com.zl.shyhttp.http.interceptor.CacheResponseInterceptor;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZhangL on 2020-06-22.
 */
public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public OkHttpEngine(Context context) {
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CacheRequestInterceptor(context.getApplicationContext()))
                .addNetworkInterceptor(new CacheResponseInterceptor())
                .build();
    }

    public OkHttpEngine() {
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public void get(Context context, String url, Map<String, Object> params, final EngineCallback callback) {
        url = HttpUtils.jointParams(url, params);


        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .tag(context);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(1000, e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String resultJson = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(resultJson);
                        if (response.code() == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(resultJson);
                                Log.e("TAG", jsonObject.toString());
                                callback.onSuccess(jsonObject.getString("data"));
                            } catch (Exception e) {
                                callback.onFailure(response.code(), resultJson);
                            }
                        } else {
                            callback.onFailure(response.code(), resultJson);
                        }
                    }
                });

            }
        });
    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, final EngineCallback callback) {
        // 了解 Okhhtp
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFailure(1000, e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        // 这个 两个回掉方法都不是在主线程中
                        final String result = response.body().string();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.code() == 200) {
                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        Log.e("TAG", jsonObject.toString());
                                        callback.onSuccess(jsonObject.getString("data"));
                                    } catch (Exception e) {
                                        callback.onFailure(response.code(), e.getMessage());
                                    }
                                } else {
                                    callback.onFailure(response.code(), result);
                                }
                            }
                        });
                    }
                }
        );
    }

    @Override
    public void download(Context context, String url, Map<String, Object> params, EngineCallback callback) {

    }

    @Override
    public void upload(Context context, String url, Map<String, Object> params, EngineCallback callback) {

    }


    /**
     * 组装post请求参数body
     */
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    // 添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    // 处理文件 --> Object File
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody
                            .create(MediaType.parse(guessMimeType(file
                                    .getAbsolutePath())), file));
                } else if (value instanceof List) {
                    // 代表提交的是 List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            // 获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody
                                    .create(MediaType.parse(guessMimeType(file
                                            .getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
