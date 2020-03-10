package com.itsmartkit.mybatis.binding;

import com.itsmartkit.mybatis.builder.MapperAnnotationBuilder;
import com.itsmartkit.mybatis.session.Configuration;
import com.itsmartkit.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MapperRegistry
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 22:30
 */
@Slf4j
public class MapperRegistry {

    private final Configuration config;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration config) {
        this.config = config;
    }


    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {

            }
            // 加入Mapper集合
            knownMappers.put(type, new MapperProxyFactory<>(type));
            // 解析Mapper
            MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
            parser.parse();
            log.debug("addMapper " + type.getName() + " success!");
        }
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        return mapperProxyFactory.newInstance(sqlSession);
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
