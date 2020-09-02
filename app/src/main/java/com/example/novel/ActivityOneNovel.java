package com.example.novel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novel.model.Message;
import com.example.novel.model.Novel;
import com.example.novel.model.NovelLine;
import com.example.novel.model.Novels;
import com.example.novel.model.OneNovel;
import com.example.novel.server.NovelAdapter;
import com.example.novel.server.NovelServer;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

import static com.example.novel.server.Method.GetGson;
import static com.example.novel.server.Method.GetInfo;
import static com.example.novel.server.Method.GetRetrofit;

public class ActivityOneNovel extends ActivityMain {
    private static final String TAG = "ActivityOneNovel";

    private List<Novels> novelList  = new ArrayList<Novels>();

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_novel);

        int novelId = getIntent().getIntExtra("novelId",0);

        if (novelId == 0){

        }else {

          getOneNovel(novelId);

//        NovelLine novelLine = setNovel(code);

            NovelAdapter novelAdapter = new NovelAdapter(ActivityOneNovel.this,R.layout.novel_item,novelList);
            ListView listView = findViewById(R.id.menu);
            listView.setAdapter(novelAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Novels novels = novelList.get(position);
                    if (position==0){
                        intent = new Intent(ActivityOneNovel.this,ActivityPageOne.class);
                        intent.putExtra("nid",novels.getNid());
                    }else {
                        Novels novels1 = novelList.get(position-1);
                        if (novels1.getChildrenNovels()==null || novels1.getChildrenNovels().length == 0){
                            intent = new Intent(ActivityOneNovel.this,ActivityPageOne.class);
                            intent.putExtra("nid",novels.getNid());
                        }else {
                            List<Novels>  novelsList = new ArrayList<>();
                            novelsList = Arrays.asList(novels1.getChildrenNovels());
                            intent = new Intent(ActivityOneNovel.this,OnePage.class);
                            intent.putExtra("novels", (Serializable) novelsList);
                        }
                    }
                    startActivity(intent);
                }
            });
        }
    }

    private void s(NovelLine novelLine){
        OneNovel oneNovel = novelLine.getOneNovel();

        while (oneNovel!=null){
            String title = oneNovel.getMNovel().getTitle();
            int uid = oneNovel.getMNovel().getUid();
            int nid = oneNovel.getMNovel().getNid();
            Novels novels = new Novels(title,uid,nid);
            novelList.add(novels);
            oneNovel = oneNovel.getOneNovel();
        }

        Novels novels = novelLine.getNovels();
        findNovel(novels);
    }

    public void findNovel(Novels novels){
        novelList.add(novels);
        if (novels.getChildrenNovels()!=null && novels.getChildrenNovels().length != 0){
            findNovel(novels.getChildrenNovels()[0]);
        }
//        for (int i=0 ;i<novels.getChildrenNovels().length;i++){
//            Novels novels1 = novels.getChildrenNovels()[i];
//            findNovel(novels1);
//        }
    }

    private void getOneNovel(int novelId){
        Retrofit retrofit = GetRetrofit();
        NovelServer novelServer = retrofit.create(NovelServer.class);
        novelServer.UserReadAll(novelId,COOK)
                .observeOn(Schedulers.io())
                .subscribe(new ResourceObserver<ResponseBody>() {
                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String code = responseBody.string();
                            Gson gson = GetGson();

                            Message message = GetInfo(code);
                            NovelLine novelLine = gson.fromJson(message.getInfo(),NovelLine.class);
                            s(novelLine);

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

    private void setNovel(String code){
        Gson gson = GetGson();

        Message message = GetInfo(code);
        NovelLine novelLine = gson.fromJson(message.getInfo(),NovelLine.class);
        s(novelLine);
    }
}