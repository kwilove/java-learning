package com.zjhuang.spring.config.custom;

import com.zjhuang.spring.entity.Role;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zjhuang
 * @create 2019/6/12 20:11
 **/
public class RoleFactoryBean implements FactoryBean<Role> {

    /**
     * 返回Bean对象
     *
     * @return
     * @throws Exception
     */
    public Role getObject() throws Exception {
        return new Role();
    }

    /**
     * 返回Bean类型
     *
     * @return
     */
    public Class<?> getObjectType() {
        return Role.class;
    }

    /**
     * 是否为单例
     *
     * @return
     */
    public boolean isSingleton() {
        return true;
    }
}
