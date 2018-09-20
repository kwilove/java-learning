package com.zjhuang.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/2 12:29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface SdAutowired {
    String value() default "";
}
