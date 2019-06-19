package com.zjhuang.spring.config.custom;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition实现类
 *
 * @author zjhuang
 * @create 2019/6/12 14:40
 **/
public class FirstCondition implements Condition {

    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 判断IOC容器中是否存在名称为user的Bean对象
        return context.getBeanFactory().containsBean("user");
    }

}
