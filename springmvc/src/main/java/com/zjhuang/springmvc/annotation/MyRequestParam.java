package com.zjhuang.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/2 12:27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface MyRequestParam {
    String value()default "";
    boolean required() default true;
}
