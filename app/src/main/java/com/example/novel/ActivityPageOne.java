package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.novel.databinding.ActivityPageOneBinding;
import com.example.novel.model.Message;
import com.example.novel.model.Novel;
import com.example.novel.model.User;
import com.example.novel.server.NovelServer;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetInfo;
import static com.example.novel.server.Method.GetRetrofit;
import static com.example.novel.server.Method.gson;

public class ActivityPageOne extends ActivityMain {
    private static final String TAG = "ActivityPageOne";
    private ActivityPageOneBinding activityPageOneBinding;
    private Novel novel;
    private User user;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPageOneBinding = DataBindingUtil.setContentView(this,R.layout.activity_page_one);

        int nid = getIntent().getIntExtra("nid",0);
        Log.e(TAG, "onCreate: "+nid);
        if (nid ==  0){

        }else {
            GetPageOne(nid);
        }

    }

    private void GetPageOne(int id){
        Retrofit retrofit = GetRetrofit();
        NovelServer novelServer = retrofit.create(NovelServer.class);

        novelServer.ReadPageOneId(id,COOK)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Throwable {
                        String code =  responseBody.string();
                        novel = getNovel(code);
                        Log.e(TAG, "onNext: "+novel );
                        activityPageOneBinding.setNovel(novel);
                        return novelServer.userMessage(novel.getUid());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String code =responseBody.string();
                            Message message = GetInfo(code);
                            user = gson.fromJson(message.getInfo(), User.class);
                            activityPageOneBinding.setUser(user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ",e.fillInStackTrace() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Novel getNovel(String code){
        Novel novel = gson.fromJson(code,Novel.class);
        return novel;
    }
}