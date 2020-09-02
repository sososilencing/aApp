package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.novel.R;
import com.example.novel.model.Novel;
import com.example.novel.model.Novels;
import com.example.novel.server.NovelAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnePage extends ActivityMain {
    private static final String TAG = "OnePage";

    private List<Novels> novelList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_page);
         novelList = (List<Novels>) getIntent().getSerializableExtra("novels");
        Log.e(TAG, "onCreate: "+novelList );
        NovelAdapter novelAdapter = new NovelAdapter(OnePage.this,R.layout.novel_item, novelList);
        ListView listView = findViewById(R.id.menu2);
        listView.setAdapter(novelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Novels novels = novelList.get(position);
                intent = new Intent(OnePage.this,ActivityPageOne.class);
                intent.putExtra("nid",novels.getNid());
                startActivity(intent);
            }
        });
    }

}