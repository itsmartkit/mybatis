package com.itsmartkit.mybatis.executor;

import com.itsmartkit.mybatis.mapping.MappedStatement;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.util.List;

/**
 * @InterfaceName Executor
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-07 02:18
 */
public interface Executor {

    int update(MappedStatement ms, Object parameter) throws SQLException;

    <E> List<E> query(MappedStatement ms, Object parameter, RowMapper rowMapper) throws SQLException;

//    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;
//
//    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

}
