package com.zjhuang.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/2 12:23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MyController {
    String value() default "";
}
