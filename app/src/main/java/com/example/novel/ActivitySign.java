package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novel.databinding.ActivitySignBinding;
import com.example.novel.model.Message;
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

public class ActivitySign extends ActivityMain implements View.OnClickListener {
    private static final String TAG = "ActivitySign";
    private ActivitySignBinding activitySignBinding;
    private EditText nickNameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignBinding = DataBindingUtil.setContentView(this,R.layout.activity_sign);
    }

    private void Sign(String email,String nickName,String password){
        Retrofit retrofit = GetRetrofit();
        UserServer userServer = retrofit.create(UserServer.class);
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickName);
        user.setPassword(password);
        Gson gson = GetGson();
        String userJson = gson.toJson(user);
        userServer.enroll(userJson)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            dialog(Json(responseBody.string()));
                        } catch (IOException e) {
                            Log.e(TAG, "onNext: "+e.getMessage() );
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private boolean Json(String code){
        Message message = GetInfo(code);
        if (message.getCode() == 400){
            Toast.makeText(getApplicationContext(),message.getInfo(),Toast.LENGTH_SHORT)
                    .show();
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enroll_sign:
                nickNameEdit = activitySignBinding.nicknameSign;
                emailEdit = activitySignBinding.emailSign;
                passwordEdit = activitySignBinding.passwordSign;

                String nickName = nickNameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                if (nickName.equals("") || email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"选项不能为空",Toast.LENGTH_SHORT)
                            .show();
                }else {
                    Sign(email,nickName,password);
                }
                break;
        }
    }

    private void dialog(boolean ok){
        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivitySign.this);
        String message;
        if (ok){
            message = "注册成功";
        }else {
            message = "注册失败";
        }
        dialog.setMessage(message)
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (ok){
                            finish();
                        }
                    }
                })
                .create()
                .show();
    }
}