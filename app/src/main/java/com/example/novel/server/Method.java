package com.example.novel.server;

import android.util.Log;

import com.example.novel.model.Message;
import com.example.novel.model.User;
import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class Method {
    public static final String BASE_URLS ="http://192.168.137.1:8080";
    private static final String TAG = "Method";
    public static Retrofit retrofit;
    public static Gson gson;
    public static User USER = new User();
    public static Retrofit GetRetrofit(){
        if (retrofit == null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URLS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @org.jetbrains.annotations.NotNull
    public static Gson GetGson(){
        if (gson==null){
            gson = new Gson();
        }
        return gson;
    }

    public static Message GetInfo(String code){
        Log.e(TAG, "GetInfo: "+code );
        Message message = gson.fromJson(code, Message.class);
        return message;
    }

}
