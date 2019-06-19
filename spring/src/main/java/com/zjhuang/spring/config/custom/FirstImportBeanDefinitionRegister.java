package com.zjhuang.spring.config.custom;

import com.zjhuang.spring.entity.Role;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zjhuang
 * @create 2019/6/12 20:04
 **/
public class FirstImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 创建一个指定类的Bean定义对象
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Role.class);
        // 将Bean定义对象注册到容器，并且自定义Bean名称为role_of_ImportBeanDefinitionRegistrar
        registry.registerBeanDefinition("role_of_ImportBeanDefinitionRegistrar", beanDefinition);
    }
}
