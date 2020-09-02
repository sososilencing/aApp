package com.example.novel.model;

public class NovelLine {
    private OneNovel OneNovel;
    private Novels Novels;

    public com.example.novel.model.Novels getNovels() {
        return Novels;
    }

    public void setNovels(com.example.novel.model.Novels novels) {
        Novels = novels;
    }

    public com.example.novel.model.OneNovel getOneNovel() {
        return OneNovel;
    }

    public void setOneNovel(com.example.novel.model.OneNovel oneNovel) {
        OneNovel = oneNovel;
    }

    @Override
    public String toString() {
        return "NovelLine{" +
                "OneNovel=" + OneNovel +
                ", Novels=" + Novels +
                '}';
    }
}
