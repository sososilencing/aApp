package com.example.novel;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.novel.databinding.ActivityUserMessageBinding;
import com.example.novel.model.Message;
import com.example.novel.model.Novel;
import com.example.novel.model.User;
import com.example.novel.server.NovelServer;
import com.example.novel.server.TitleAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetInfo;
import static com.example.novel.server.Method.GetRetrofit;
import static com.example.novel.server.Method.gson;

public class ActivityUserMessage extends ActivityMain {
    private static final String TAG = "ActivityUserMessage";
    private ActivityUserMessageBinding activityUserMessageBinding;
    private Intent intent;
    private List<Novel> novelList;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUserMessageBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_message);
        int id = getIntent().getIntExtra("name",0);
        if (id == 0){

        }else {
            getThing(id);
        }

    }

    private void getThing(int uid){
        Retrofit retrofit = GetRetrofit();

        NovelServer novelServer = retrofit.create(NovelServer.class);

        Observable<ResponseBody> userMessageObservable = novelServer.userMessage(uid);
        Observable<ResponseBody> userNovelAll = novelServer.userNovelAll(uid);
        Observable<Object> merge = Observable.merge(userMessageObservable,userNovelAll);
        merge.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        ResponseBody responseBody = (ResponseBody) o;

                        try {
                            Message message = GetInfo(responseBody.string());
                            if (message.getCode() == 999 ){
                                Novel[] novels = gson.fromJson(message.getInfo(), Novel[].class);
                                novelList = Arrays.asList(novels);
                                TitleAdapter titleAdapter = new TitleAdapter(ActivityUserMessage.this,R.layout.title_item,novelList);
                                listView = findViewById(R.id.list_title);
                                listView.setAdapter(titleAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Novel novel = novelList.get(position);
                                        intent = new Intent(getApplicationContext(),ActivityPageOne.class);
                                        intent.putExtra("nid",novel.getNid());
                                        startActivity(intent);
                                    }
                                });
                            }else {
                                User user =gson.fromJson(message.getInfo(),User.class);
                                activityUserMessageBinding.setUser(user);
                            }

                            Log.e(TAG, "onNext: "+responseBody.string() );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}