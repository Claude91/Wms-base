package com.shqtn.base;

import com.shqtn.base.info.ApiUrl;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private final int dd = 222;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        int i = 0;
        double b = i;

        b = 2.3d;
    }

    @Test
    public void remvoeData() {
        ArrayList<Bean> list = new ArrayList<>();

        Bean e = new Bean();
        list.add(e);
        for (int i = 0; i < 20; i++) {
            list.add(new Bean());
        }


        System.out.print(list.size() + "现在数量");
        list.remove(e);
        System.out.print("删除后的数量" + list.size());
    }


    public static class Bean {
        String f;
    }
}