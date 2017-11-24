package com.shqtn.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * @author ql
 *         创建时间:2017/11/24
 *         描述:
 */

public class LayoutUtils {

    public static void bind(Object object) {
        Class<?> aClass = object.getClass();
        if (!aClass.isAnnotationPresent(Layout.class)) {
            return;
        }

        Layout annotation = aClass.getAnnotation(Layout.class);
        if (annotation.layoutRes() == 0) {
            try {
                Method setContentView = aClass.getMethod("setContentView", int.class);
                if (setContentView != null) {
                    setContentView.invoke(object, annotation.layoutRes());
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
