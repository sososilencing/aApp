package com.example.novel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.novel.databinding.ActivityRandNovelBinding;
import com.example.novel.model.Message;
import com.example.novel.model.Novel;
import com.example.novel.model.OneNovel;
import com.example.novel.model.User;
import com.example.novel.server.NovelServer;
import com.example.novel.server.UserServer;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetInfo;
import static com.example.novel.server.Method.GetRetrofit;
import static com.example.novel.server.Method.USER;

public class ActivityRandNovel extends ActivityMain implements View.OnClickListener {
    private static final String TAG = "ActivityRandNovel";
    private ActivityRandNovelBinding randNovelBinding;

    private Novel novel;
    private User user;
    private Gson gson = GetGson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randNovelBinding = DataBindingUtil.setContentView(this,R.layout.activity_rand_novel);
        RandNovel();

        RefreshLayout refreshLayout = randNovelBinding.refresh;

        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                RandNovel();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void RandNovel(){
        Retrofit retrofit = GetRetrofit();
        NovelServer novelServer = retrofit.create(NovelServer.class);

        novelServer.ReadPageRandom()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Throwable {
                        novel = Json(responseBody.string());
                        randNovelBinding.setNovel(novel);
                        return novelServer.userMessage(novel.getUid());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String code =responseBody.string();
                            Message message = GetInfo(code);
                            user = gson.fromJson(message.getInfo(),User.class);
                            randNovelBinding.setUser(user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private Novel Json(String code){
        Message message = GetInfo(code);
        Novel novel = gson.fromJson(message.getInfo(), Novel.class);
        Log.e(TAG, "Json: "+novel );
        return novel;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Log.e(TAG, "onClick: "+v.getId() +R.id.title);
        switch (v.getId()){
            case R.id.author:
                intent = new Intent(this,ActivityUserMessage.class);
                intent.putExtra("name",user.getId());
                startActivity(intent);
                break;
            case R.id.write:
                intent = new Intent(this,ActivityWrite.class);
                startActivity(intent);
                break;
            case R.id.sequel:
                intent = new Intent(this,ActivityWrite.class);
                intent.putExtra("pid",novel.getNid());
                startActivity(intent);
                break;
            case R.id.title:
                intent = new Intent(this, ActivityOneNovel.class);
                intent.putExtra("novelId",novel.getNid());
                startActivity(intent);
                break;
            case R.id.user_message:
                intent = new Intent(this,ActivityUserMessage.class);
                intent.putExtra("name",USER.getId());
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}