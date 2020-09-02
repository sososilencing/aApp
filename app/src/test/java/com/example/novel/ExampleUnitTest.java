package com.example.novel;

import com.example.novel.model.Message;
import com.example.novel.model.Novel;
import com.example.novel.model.NovelLine;
import com.example.novel.model.Novels;
import com.example.novel.model.OneNovel;
import com.example.novel.model.User;
import com.example.novel.server.NovelServer;
import com.example.novel.server.UserServer;
import com.google.gson.Gson;

import org.junit.Test;

import java.lang.reflect.Type;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.ResourceObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        String code ="{\n" +
                "    \"Code\": 200,\n" +
                "    \"Info\": \"{\\\"OneNovel\\\":null,\\\"Novels\\\":{\\\"Nid\\\":1,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":0,\\\"ChildrenNovels\\\":[{\\\"Nid\\\":2,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":1,\\\"ChildrenNovels\\\":[{\\\"Nid\\\":6,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":2,\\\"ChildrenNovels\\\":[{\\\"Nid\\\":8,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":6,\\\"ChildrenNovels\\\":[]},{\\\"Nid\\\":9,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没技术\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":6,\\\"ChildrenNovels\\\":[]}]},{\\\"Nid\\\":7,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":2,\\\"ChildrenNovels\\\":[]}]},{\\\"Nid\\\":3,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":1,\\\"ChildrenNovels\\\":[{\\\"Nid\\\":4,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":3,\\\"ChildrenNovels\\\":[]},{\\\"Nid\\\":5,\\\"Uid\\\":1,\\\"Title\\\":\\\"开始就还没结束\\\",\\\"Context\\\":\\\"\\\",\\\"Pid\\\":3,\\\"ChildrenNovels\\\":[]}]}]}}\"\n" +
                "}";
        Gson gson = new Gson();
        Message message = gson.fromJson(code,Message.class);
        System.out.println(message.getInfo());

        NovelLine novelLine = gson.fromJson(message.getInfo(),NovelLine.class);

        System.out.println(novelLine);

        OneNovel oneNovel = novelLine.getOneNovel();

        while (oneNovel!=null){
            oneNovel = oneNovel.getOneNovel();
            System.out.println(1);
        }

        Novels novels = novelLine.getNovels();
        findNovel(novels);

    }

    public void findNovel(Novels novels){
        System.out.println(novels);
        for (int i= 0 ;i<novels.getChildrenNovels().length;i++){
            Novels novels1 = novels.getChildrenNovels()[i];
            System.out.println(novels1);
        }
    }

    public void xx(){


    }

}