package com.zjhuang.springboot.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author zjhuang
 * @create 2019/6/6 17:54
 **/
@Component
public class PropertySourcesPlaceholderConfigurerBean {
    @Bean
    public PropertySourcesPlaceholderConfigurer yaml() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("test.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
