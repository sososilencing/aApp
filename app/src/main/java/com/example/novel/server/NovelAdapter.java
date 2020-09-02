package com.example.novel.server;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novel.R;
import com.example.novel.model.Novel;
import com.example.novel.model.Novels;

import java.util.List;

public class NovelAdapter extends ArrayAdapter<Novels> {
    private static final String TAG = "NovelAdapter";
    private int novelId;
    public NovelAdapter(@NonNull Context context, int resource, List<Novels> novels) {
        super(context, resource,novels);
        novelId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Novels novel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(novelId, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.novel_title);
            viewHolder.author = view.findViewById(R.id.novel_author);
            viewHolder.pageNum = view.findViewById(R.id.page_num);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Log.e(TAG, "getView: "+novel );
        viewHolder.title.setText(novel.getTitle());
        viewHolder.author.setText(String.valueOf(novel.getUid()));

        int num = position +1;
        if(position==0){
            viewHolder.pageNum.setText("第"+num+"章");
        }else {
            Novels novel1 = getItem(position-1);
            if (novel1.getChildrenNovels()== null || novel1.getChildrenNovels().length == 0){
                viewHolder.pageNum.setText("第"+num+"章");
            }else {
                viewHolder.pageNum.setText("第"+num+"章"+"间章");
            }
        }
        return view;
    }

    class ViewHolder{
        TextView title ;
        TextView author ;
        TextView pageNum ;
    }
}
