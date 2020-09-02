package com.example.novel.server;

import com.example.novel.model.User;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserServer {
    @FormUrlEncoded
    @POST("/enroll")
    Observable<ResponseBody> enroll(@Field("user") String user);

    @FormUrlEncoded
    @POST("/login/user")
    Observable<ResponseBody> login(@Field("user") String user);

}
