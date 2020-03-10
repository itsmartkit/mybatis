package com.itsmartkit.mybatis.session;


import com.itsmartkit.mybatis.binding.MapperRegistry;
import com.itsmartkit.mybatis.mapping.MappedStatement;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Configuration
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 22:31
 */
public class Configuration {

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    private Configuration() {}

    private static class Inner {
        static Configuration config = new Configuration();
    }

    public static Configuration getInstance() {
        return Inner.config;
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public boolean hasMappedStatement(MappedStatement ms) {
        return mappedStatements.containsKey(ms.getId());
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

}
