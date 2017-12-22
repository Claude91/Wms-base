package com.shqtn.wms;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import org.apache.commons.validator.Msg;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
    public void testString() {
        String s = String.format("%s:", "测试");
        System.out.print(s);
    }

    @Test
    public void testHandler(){

    }

    @Test
    public void testClassField() {
        final A a = new A("33");
        Class<? extends A> aClass = a.getClass();
        Object o = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IA.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                System.out.println("invoke ---->" + name);
                Handler handler;
                return method.invoke(a, args);
            }
        });
        a.b = 100;
        String name = o.getClass().getName();
        System.out.println(name);
        IA ia = (IA) o;
        ia.setContent("打印了哦");
        System.out.println(ia.getInt());

    }

    public interface IA {
        void setContent(String s);

        int getInt();

        String getString();
    }

    public class A implements IA {
        String a;
        int b;


        public A(String a) {
            this.a = a;
        }

        public void setFFF() {
            System.out.println("测试");
        }

        public void setFFF1() {
            System.out.println("测试");
        }

        public void setFFF2() {
            System.out.println("测试");
        }

        public void setFFF3() {
            System.out.println("测试");
        }

        public void setFFF4() {
            System.out.println("测试");
        }

        @Override
        public void setContent(String s) {
            System.out.println("测试  - setContent = " + s);
        }

        @Override
        public int getInt() {
            return b;
        }

        @Override
        public String getString() {
            return null;
        }
    }
}