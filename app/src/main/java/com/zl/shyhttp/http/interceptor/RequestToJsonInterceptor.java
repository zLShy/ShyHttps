package com.zl.shyhttp.http.interceptor;

import android.content.Context;
import android.util.Log;

import com.zl.shyhttp.utils.AnalyticalJSON;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZhangL on 2020-04-07. 拦截传输数据转换为JSON
 */
public class RequestToJsonInterceptor implements Interceptor {

    Context mContext;

    public RequestToJsonInterceptor(Context context) {
        this.mContext = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();

        Map<String, Object> params = new HashMap<>();

        if (request.body() instanceof FormBody) {
            // 构造新的请求表单
            FormBody.Builder builder = new FormBody.Builder(Charset.forName("UTF-8"));

            FormBody body = (FormBody) request.body();
            //将以前的参数添加
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < body.size(); i++) {
                builder.add(body.encodedName(i), body.encodedValue(i));
                String[] spliteKey = body.encodedName(i).split("_");
                try {
                    if (spliteKey[1].equals("Int")) {
                        jsonObject.put(spliteKey[0], Integer.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("String")) {
                        jsonObject.put(spliteKey[0], String.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("Double")) {
                        jsonObject.put(spliteKey[0], Double.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("Float")) {
                        jsonObject.put(spliteKey[0], Float.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("Long")) {
                        jsonObject.put(spliteKey[0], Long.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("Boolean")) {
                        jsonObject.put(spliteKey[0], Boolean.valueOf(URLDecoder.decode(body.encodedValue(i), "UTF-8")));
                    } else if (spliteKey[1].equals("Object")) {
                        HashMap<String, Object> maps = AnalyticalJSON.getObjHashMap(URLDecoder.decode(body.encodedValue(i), "UTF-8"));
                        JSONObject objCodeJson = new JSONObject();
                        for (Map.Entry<String, Object> entry : maps.entrySet()) {
                            String mapKey = entry.getKey();
                            Object mapValue = entry.getValue();
                            Log.e("TAG", mapKey + "===" + mapValue.toString());
                            if (mapValue instanceof Integer) {
                                objCodeJson.put(mapKey, Integer.valueOf(mapValue.toString()));
                            } else if (mapValue instanceof String) {
                                objCodeJson.put(mapKey, mapValue.toString());
                            } else if (mapValue instanceof Double) {
                                objCodeJson.put(mapKey, Double.valueOf(mapValue.toString()));
                            } else if (mapValue instanceof Float) {
                                objCodeJson.put(mapKey, Float.valueOf(mapValue.toString()));
                            } else if (mapValue instanceof Long) {
                                objCodeJson.put(mapKey, Long.valueOf(mapValue.toString()));
                            } else if (mapValue instanceof Boolean) {
                                objCodeJson.put(mapKey, Boolean.valueOf(mapValue.toString()));
                            }
                        }
                        jsonObject.put(spliteKey[0], objCodeJson);
//                        if (spliteKey[0].equals("smsCodeInfo")){
//                            JSONObject smsCodeJson = new JSONObject();
//                            JSONObject sourceJson = new JSONObject(URLDecoder.decode(body.encodedValue(i), "UTF-8"));
//                            smsCodeJson.put("code", sourceJson.getString("code"));
//                            smsCodeJson.put("phone", sourceJson.getString("phone"));
//                            smsCodeJson.put("modular", sourceJson.getInt("modular"));
//                            jsonObject.put("smsCodeInfo", smsCodeJson);
//                        }else {
//                            jsonObject.put(spliteKey[0],URLDecoder.decode(body.encodedValue(i),"UTF-8"));
//                        }
                    }
//                    if (URLDecoder.decode(body.encodedName(i),"UTF-8").equals("smsCodeInfo")) {
//                        JSONObject smsCodeJson = new JSONObject();
//                        JSONObject sourceJson = new JSONObject(URLDecoder.decode(body.encodedValue(i),"UTF-8"));
//                        smsCodeJson.put("code",sourceJson.getString("code"));
//                        smsCodeJson.put("phone",sourceJson.getString("phone"));
//                        smsCodeJson.put("modular",sourceJson.getInt("modular"));
//                        Log.e("TAG",sourceJson.getString("code")+"="+sourceJson.getInt("modular"));
//                        jsonObject.put("smsCodeInfo",smsCodeJson);
//                    }else {
//                        jsonObject.put(URLDecoder.decode(body.encodedName(i),"UTF-8"),URLDecoder.decode(body.encodedValue(i),"UTF-8"));
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//
            }
            String JSON = jsonObject.toString();
            Log.e("TAG", "--->" + JSON);
            RequestBody newRequestBody = RequestBody.create(JSON, MediaType.parse("application/json;charset=utf-8"));

            request = request.newBuilder()
                    .post(newRequestBody)
                    .build();
//            return chain.proceed(request);
        }

        Log.e("TAG", "url-->" + request.url());
        return chain.proceed(request);
    }
}
