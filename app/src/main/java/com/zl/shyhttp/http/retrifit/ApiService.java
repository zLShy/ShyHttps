package com.zl.shyhttp.http.retrifit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {

    @GET("")
    Observable<Response<ResponseBody>> GetMethod(@Url String url, @QueryMap Map<String, Object> map);

    @GET("")
    Observable<Response<ResponseBody>> GetMethod(@Url String url);

    @FormUrlEncoded
    @POST("")
    Observable<Response<ResponseBody>> PostMethod(@Url String url, @FieldMap Map<String, Object> params);

    @POST("")
    Observable<Response<ResponseBody>> PostMethod(@Url String url);

    @Multipart
    @POST("")
    Observable<Response<ResponseBody>> upload(@Url String url, @PartMap Map<String, RequestBody> params);
}
