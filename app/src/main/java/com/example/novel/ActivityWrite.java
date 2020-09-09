package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.novel.databinding.ActivityWriteBinding;
import com.example.novel.model.Novel;
import com.example.novel.model.User;
import com.example.novel.server.NovelServer;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetRetrofit;
import static com.example.novel.server.Method.USER;
import static com.example.novel.server.Method.gson;

public class ActivityWrite extends ActivityMain implements View.OnClickListener {
    private static final String TAG = "ActivityWrite";
    private ActivityWriteBinding activityWriteBinding;
    private Novel novel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWriteBinding  = DataBindingUtil.setContentView(this,R.layout.activity_write);
        int pid = getIntent().getIntExtra("pid",0);
        novel = new Novel();
        novel.setUid(USER.getId());
        novel.setPid(pid);
        activityWriteBinding.setNovel(novel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void write(){
        Retrofit retrofit = GetRetrofit();
        NovelServer novelServer = retrofit.create(NovelServer.class);
        Gson gson = GetGson();
        String code = gson.toJson(novel);
        novelServer.UserWriteTitle(code,COOK)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                String title = activityWriteBinding.title.getText().toString();
                String context = activityWriteBinding.context.getText().toString();
                if (!title.equals("") && !context.equals("")){
                    novel.setTitle(title);
                    novel.setContext(context);
                    write();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"题目或者内容不能为空哦~",Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                break;
        }
    }
}