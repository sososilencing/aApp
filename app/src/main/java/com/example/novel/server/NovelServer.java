package com.example.novel.server;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NovelServer {
    @FormUrlEncoded
    @POST("/user/write/title")
    Observable<ResponseBody> UserWriteTitle(@Field("novel") String novel,@Header("cook") String cook);

    @GET("/user/read/all/{nid}")
    Observable<ResponseBody> UserReadAll(@Path("nid") int nid, @Header("cook") String cook);

    @GET("/read/page/random")
    Observable<ResponseBody> ReadPageRandom();

    @GET("/read/page/one/{id}")
    Observable<ResponseBody> ReadPageOneId(@Path("id") int id, @Header("cook") String cook);

    @GET("/get/user/{uid}")
    Observable<ResponseBody> userMessage(@Path("uid") int uid);

    @GET("/get/novel/{uid}")
    Observable<ResponseBody> userNovelAll(@Path("uid") int uid);
}
