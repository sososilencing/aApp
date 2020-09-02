package com.example.novel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.novel.databinding.ActivityMainBinding;
import com.example.novel.model.User;
import com.example.novel.server.ActivityController;
import com.example.novel.server.NovelServer;
import com.example.novel.server.UserServer;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.ResourceObserver;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetRetrofit;

public class ActivityMain extends AppCompatActivity {
    private static final String TAG = "ActivityMain";
    public static String COOK = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }


    private void test(){


        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        User user = new User();

        Retrofit retrofit = GetRetrofit();
        NovelServer novelServer = retrofit.create(NovelServer.class);

        novelServer.ReadPageRandom()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            Toast.makeText(getApplicationContext(),"????",Toast.LENGTH_SHORT)
                                    .show();
                            Toast.makeText(getApplicationContext(),responseBody.string(),Toast.LENGTH_SHORT).show();
                            Log.e(TAG, responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
        activityMainBinding.setUser(user);
//      setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}