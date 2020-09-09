package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novel.databinding.ActivityLoginBinding;
import com.example.novel.model.Message;
import com.example.novel.model.TokenUser;
import com.example.novel.model.User;
import com.example.novel.server.UserServer;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetInfo;
import static com.example.novel.server.Method.GetRetrofit;
import static com.example.novel.server.Method.USER;

public class ActivityLogin extends ActivityMain implements View.OnClickListener {
    private static final String TAG = "ActivityLogin";
    private ActivityLoginBinding loginBinding;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        accountEdit = loginBinding.account;
        passwordEdit = loginBinding.password;

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user","");

        if (!userJson.equals("")){
            try {
                Gson gson = GetGson();
                User user = gson.fromJson(userJson,User.class);
                if (!user.getEmail().equals("") && !user.getPassword().equals("")){
                    login(user.getEmail(),user.getPassword());
                }else {
                    sharedPreferences.edit().clear().apply();
                    recreate();
                }
            }catch (Exception e){
                sharedPreferences.edit().clear().apply();
                Log.e(TAG, "onCreate: ",e );
                recreate();
            }
        }
    }

    public void login(String email,String password) {
        Retrofit retrofit = GetRetrofit();

        UserServer userServer = retrofit.create(UserServer.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Gson gson = GetGson();
        String userJson = gson.toJson(user);
        userServer.login(userJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    //这里可以继续搞逻辑处理并且同步
                    //现在需要把得到tokenUser 往下传 并且以后请求需要包含token
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                           TokenUser tokenUser = Json(responseBody.string());

                            if (tokenUser==null){
                                Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT)
                                        .show();
                            }else {
                                SharedPreferences share = getSharedPreferences("login",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = share.edit();
                                String json = gson.toJson(tokenUser.getUser());
                                editor.putString("user",json);
                                editor.apply();
                                USER = tokenUser.getUser();
                                COOK = tokenUser.getToken();
                                intent = new Intent(ActivityLogin.this, ActivityRandNovel.class);
                                startActivity(intent);  // 这里之前要不userToken 传过去
                                finish();
                            }
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
    }

    private TokenUser Json(String code){
        Gson gson = GetGson();
        Message message  = GetInfo(code); // 这波啊 得到 tokenUser
        System.out.println(message);
        if ( message.getCode() ==  400 ){
            return null;
        }else {
            TokenUser tokenUser = gson.fromJson(message.getInfo(), TokenUser.class);
            return tokenUser;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:

                String account =  accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (account.equals("") || password.equals("")){
                    //不能为空
                    Toast.makeText(getApplicationContext(),"账号或密码不能为空",Toast.LENGTH_SHORT)
                            .show();
                }else {
                    login(account,password);
                }
                break;
            case R.id.sign:
                intent = new Intent(ActivityLogin.this, ActivitySign.class);
                startActivity(intent);
                break;
        }
    }

}