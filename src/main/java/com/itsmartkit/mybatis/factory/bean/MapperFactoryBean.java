package com.itsmartkit.mybatis.factory.bean;

import com.itsmartkit.mybatis.session.SqlSessionSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName MapperFactoryBean
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 01:55
 */
@Slf4j
public class MapperFactoryBean<T> extends SqlSessionSupport implements FactoryBean<T> {

    private final Class<T> mapperInterface;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    protected void checkConfig() {
        super.checkConfig();
        // addMapper
        getSqlSession().addMapper(mapperInterface);
    }
}
