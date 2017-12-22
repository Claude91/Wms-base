package com.shqtn.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ql
 *         创建时间:2017/11/24
 *         描述:
 */

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Layout {
    String title();

    int layoutRes();

    boolean isShowBack();

    /**
     * 返回键资源id
     * @return
     */
    int backRes();
}
