package com.zjhuang.springboot.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author zjhuang
 * @create 2019/6/6 17:29
 **/
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        // Version: Springboot 1.5.*
//         return name != null ?
//                 new PropertySourcesLoader().load(encodedResource.getResource(), name, null)
//                 : new PropertySourcesLoader().load(encodedResource.getResource(), getNameForResource(encodedResource.getResource()), null);

        // Version: Springboot 2.1.5.RELEASE
        Resource resource = encodedResource.getResource();
        return name != null ?
                new YamlPropertySourceLoader().load(name, resource).get(0) :
                new YamlPropertySourceLoader().load(getNameForResource(resource), resource).get(0);
    }

    private static String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if (!StringUtils.hasText(name)) {
            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }
        return name;
    }
}
