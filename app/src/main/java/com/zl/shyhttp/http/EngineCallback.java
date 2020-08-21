package com.zl.shyhttp.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by hcDarren on 2017/12/24.
 */

public interface EngineCallback {

    // 开始执行，在执行之前会回掉的方法
    public void onPreExecute(Context context, Map<String, Object> params);

    // 返回可以直接操作的对象,每次在上层自己用字符串去解析会蛋疼
    public void onSuccess(String result);

    public void onFailure(int code, String msg);


    public final EngineCallback DEFUALT_CALL_BACK = new EngineCallback() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }
        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onFailure(int code, String msg) {

        }
    };
}
