package com.itsmartkit.mybatis.session;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SqlSession
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 22:40
 */
public interface SqlSession {

    Configuration getConfiguration();

    <T> T getMapper(Class<T> type);

    <T> void addMapper(Class<T> type);

    <T> T selectOne(String statement);

    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement);

    <E> List<E> selectList(String statement, Object parameter);

    <K, V> Map<K, V> selectMap(String statement, String mapKey);

    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);

    int insert(String statement);


    int insert(String statement, Object parameter);


    int update(String statement);


    int update(String statement, Object parameter);


    int delete(String statement);

    int delete(String statement, Object parameter);


}
