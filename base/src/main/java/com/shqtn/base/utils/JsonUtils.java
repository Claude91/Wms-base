package com.shqtn.base.utils;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-5-13.
 */
public class JsonUtils {

    private static final Gson sGson = new Gson();

    public static String toJson(Object object) {
        return sGson.toJson(object);
    }

    public static String toJson(ArrayList<Object> array) {
        return sGson.toJson(array);
    }

    public static <T> T getObject(String json, Class clazz) {
        return (T) sGson.fromJson(json, clazz);
    }
    public static String toJson(Map map){
        return sGson.toJson(map);
    }

    public static Map<String, String> toMap(Object object) {
        Class clazz = object.getClass();
        Map<String, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sb = new StringBuffer("get");
        for (Field f : fields) {
            String key = f.getName();
            f.setAccessible(true);
            sb.delete(0,sb.length());
            String c = key.substring(0,1);
            sb.append(c.toUpperCase())
            .append(key.substring(1,key.length()));

            try {
                Method getMethod = clazz.getDeclaredMethod(sb.toString());
                Object values = getMethod.invoke(object);
                if (!Modifier.isStatic(f.getModifiers()) && values !=null)
                    map.put(key,values.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch (NoSuchMethodException e){

            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
