package com.zl.shyhttp.http;

import android.content.Context;

import com.zl.shyhttp.http.okhttp.OkHttpEngine;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangL on 2020-06-22.
 */
public class HttpUtils {
    // 直接带参数 ，链式调用
    private String mUrl;

    // 请求方式
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0022;

    private Map<String, Object> mParams;

    // 上下文
    private Context mContext;


    // 默认OkHttpEngine
    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    // 在Application初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }


    private HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils get() {

        mType = GET_TYPE;
        return this;
    }

    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    // 添加参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    //切换库
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    private void get(String url, Map<String, Object> params, EngineCallback callBack) {
        mHttpEngine.get(mContext, url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallback callBack) {
        mHttpEngine.post(mContext, url, params, callBack);
    }


    /**
     * 拼接参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }

        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        return stringBuffer.toString();
    }

    public void execute(EngineCallback callBack){
        // 每次执行都会进入这个方法：这里添加是行不通的
        // 1.baseLibrary里面这里面不包含业务逻辑
        // 2.如果有多条业务线，
        // 让callBack回调去

        if(callBack == null){
            callBack = EngineCallback.DEFUALT_CALL_BACK;
        }

        // 判断执行方法
        if(mType == POST_TYPE){
            post(mUrl,mParams,callBack);
        }

        if(mType == GET_TYPE){
            get(mUrl, mParams, callBack);
        }
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
