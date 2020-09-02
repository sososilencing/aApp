package com.example.novel.model;

import java.io.Serializable;

public class Novel implements Serializable {
    private int Nid;
    private int Uid;
    private String Title;
    private String Context;
    private int Pid;

    public Novel(){

    }

    public Novel(String title,int uid){
        Title = title;
        Uid = uid;
    }
    public int getNid() {
        return Nid;
    }

    public void setNid(int nid) {
        Nid = nid;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    @Override
    public String toString() {
        return "Novel{" +
                "Nid=" + Nid +
                ", Uid=" + Uid +
                ", Title='" + Title + '\'' +
                ", Context='" + Context + '\'' +
                ", Pid=" + Pid +
                '}';
    }
}
