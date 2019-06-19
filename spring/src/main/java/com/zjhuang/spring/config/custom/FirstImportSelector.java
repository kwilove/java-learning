package com.zjhuang.spring.config.custom;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zjhuang
 * @create 2019/6/12 15:55
 **/
public class FirstImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[] {"com.zjhuang.spring.entity.Role"};
    }

}
