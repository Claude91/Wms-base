package com.shqtn.enter;

import com.shqtn.enter.utils.ThreadHelper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testThread() {
        ThreadHelper helper = new ThreadHelper();
        helper.createSingle();
        for (int i = 0; i < 400; i++) {
            final int ad = i;
            helper.add(new Runnable() {
                @Override
                public void run() {
                    System.out.println("打印成功" + ad);
                }
            });
        }

    }

    @Test
    public void testInteger(){
        String s = "-100";
        int i = Integer.parseInt(s);

        i = i ;

    }
}