package com.zjhuang.springmvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/2 12:29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MyResource {
    String value() default "";
}
