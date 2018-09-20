package com.zjhuang.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2018/8/2 12:27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface SdRequestMapping {
    String value() default "";
}
