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
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.List;

public class TitleAdapter extends ArrayAdapter<Novel> {
    private int  id;

    public TitleAdapter(@NonNull Context context, int resource, List<Novel> novels) {
        super(context, resource,novels);
        id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Novel novel = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(id, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.novel_title);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }


      viewHolder.title.setText(novel.getTitle());
        return view;
    }

    class ViewHolder{
        TextView title ;
    }
}
