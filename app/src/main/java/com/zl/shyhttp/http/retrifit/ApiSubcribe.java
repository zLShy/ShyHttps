package com.zl.shyhttp.http.retrifit;


import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.RequiresApi;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;


final class ApiSubcribe {

    private static ApiSubcribe mInstance;

    private ApiSubcribe() {
    }

    public static ApiSubcribe getInstance() {
        if (mInstance == null) {
            synchronized (ApiSubcribe.class) {
                if (mInstance == null) {
                    mInstance = new ApiSubcribe();
                }
            }
        }

        return mInstance;
    }

    private void Subcribe(Observer<Response<ResponseBody>> mObserver, Observable<Response<ResponseBody>> mObserverable) {

        mObserverable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void GetMethod(String url, Map<String, Object> parems, RealObserver observer) {
        Subcribe(observer, RetrifitClient.getInstance().initApiService().GetMethod(url, parems));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void GetMethod(String url, RealObserver observer) {
        Subcribe(observer, RetrifitClient.getInstance().initApiService().GetMethod(url));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void PostMethod(String url, Map<String, Object> parems, RealObserver observer) {
        Subcribe(observer, RetrifitClient.getInstance().initApiService().PostMethod(url, intiMap(parems)));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void PostMethod(String url, RealObserver observer) {
        Subcribe(observer, RetrifitClient.getInstance().initApiService().PostMethod(url));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UpLoadMethod(String url, Map<String, RequestBody> parems, RealObserver observer) {
        Subcribe(observer, RetrifitClient.getInstance().initApiService().upload(url, parems));
    }


    private Map<String,Object> intiMap(Map<String, Object> parems) {
        Map<String,Object> map = new HashMap<>(parems.size());
        for(Map.Entry<String, Object> entry : parems.entrySet()){
            String mapKey = entry.getKey();
            Object mapValue = entry.getValue();
            if (mapValue instanceof Integer) {
                map.put(mapKey+"_Int",mapValue);
            }else if (mapValue instanceof Boolean) {
                map.put(mapKey+"_Boolean",mapValue);
            }else if (mapValue instanceof String) {
                map.put(mapKey+"_String",mapValue);
            }else if (mapValue instanceof Double) {
                map.put(mapKey+"_Double",mapValue);
            }else if (mapValue instanceof Float){
                map.put(mapKey+"_Float",mapValue);
            }else if (mapValue instanceof Long) {
                map.put(mapKey+"_Long",mapValue);
            }else if (mapValue instanceof Object) {
                map.put(mapKey+"_Object",mapValue);
            }
        }

        return map;
    }
}
