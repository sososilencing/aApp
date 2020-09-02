package com.example.novel.model;

import androidx.annotation.NonNull;

public class Message {
    private int Code;
    private  String Info;

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        this.Info = info;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    @NonNull
    @Override
    public String toString() {
        return Code+Info;
    }
}
