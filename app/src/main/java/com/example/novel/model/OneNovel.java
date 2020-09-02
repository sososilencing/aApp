package com.example.novel.model;

public class OneNovel {
    private Novel MNovel;
    private OneNovel OneNovel;

    public com.example.novel.model.OneNovel getOneNovel() {
        return OneNovel;
    }

    public void setOneNovel(com.example.novel.model.OneNovel oneNovel) {
        OneNovel = oneNovel;
    }


    public Novel getMNovel() {
        return MNovel;
    }

    public void setMNovel(Novel MNovel) {
        this.MNovel = MNovel;
    }

    @Override
    public String toString() {
        return "OneNovel{" +
                "MNovel=" + MNovel +
                ", OneNovel=" + OneNovel +
                '}';
    }
}
