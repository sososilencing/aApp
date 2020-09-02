package com.example.novel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.novel.server.NovelServer;

import java.util.Arrays;

public class Novels implements Parcelable {
    private int Nid;
    private int Uid;
    private String Title;
    private String Context;
    private int Pid;
    private Novels[] ChildrenNovels;

    public Novels() {

    }

    public Novels(String title, int uid, int nid) {
        Title = title;
        Uid = uid;
        Nid = nid;
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

    public Novels[] getChildrenNovels() {
        return ChildrenNovels;
    }

    public void setChildrenNovels(Novels[] childrenNovels) {
        ChildrenNovels = childrenNovels;
    }

    @Override
    public String toString() {
        return "Novels{" +
                "Nid=" + Nid +
                ", Uid=" + Uid +
                ", Title='" + Title + '\'' +
                ", Context='" + Context + '\'' +
                ", Pid=" + Pid +
                ", ChildrenNovels=" + Arrays.toString(ChildrenNovels) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Nid);
        dest.writeInt(Uid);
        dest.writeString(Title);
        dest.writeString(Context);
        dest.writeInt(Pid);
    }

    public static final Parcelable.Creator<Novels> CREATOR = new Creator<Novels>() {
        @Override
        public Novels createFromParcel(Parcel source) {
            Novels novels = new Novels();
            novels.Nid = source.readInt();
            novels.Uid = source.readInt();
            novels.Title = source.readString();
            novels.Context = source.readString();
            novels.Pid = source.readInt();
            return novels;
        }

        @Override
        public Novels[] newArray(int size) {
            return new Novels[size];
        }
    };
}
