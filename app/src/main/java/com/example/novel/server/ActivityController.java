package com.example.novel.server;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {
    public static List<AppCompatActivity> activities = new ArrayList<AppCompatActivity>();
    public static void addActivity(AppCompatActivity activity){
        activities.add(activity);
    }
    public static void removeActivity(AppCompatActivity appCompatActivity){
        activities.remove(appCompatActivity);
    }
    public static void finishAll(){
        for (AppCompatActivity appCompatActivity:activities){
            if(!appCompatActivity.isFinishing())
                appCompatActivity.finish();
        }
    }
}
