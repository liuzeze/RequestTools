package com.lz.rxrequestplugin;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-16       创建class
 */
public interface RequestApi {

    @GET("v2/book/1220562")
    Observable<String> getNews();


    @POST()
    @FormUrlEncoded
    Observable<List<FileBean>> getFilelist(@Url String url, @Field("path") String path);
}
