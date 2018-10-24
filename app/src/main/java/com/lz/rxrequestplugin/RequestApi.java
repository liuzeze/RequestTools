package com.lz.rxrequestplugin;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2018-10-16       创建class
 */
public interface RequestApi {

    @GET("v2/book/1220562")
    Observable<String> getNews();


    @GET()
    Observable<String> getFilelist(@Url String url);
}
