package com.itsmartkit.mybatis.session;

import com.itsmartkit.mybatis.executor.Executor;
import com.itsmartkit.mybatis.executor.SimpleExecutor;
import com.itsmartkit.mybatis.mapping.MappedStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DefaultSqlSession
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 02:27
 */
@Slf4j
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, NamedParameterJdbcTemplate jdbcTemplate) {
        this.configuration = configuration;
        this.jdbcTemplate = jdbcTemplate;
        this.executor = new SimpleExecutor(jdbcTemplate);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type,this);
    }

    @Override
    public <T> void addMapper(Class<T> type) {
        configuration.addMapper(type);
    }

    @Override
    public <T> T selectOne(String statement) {
        return selectOne(statement, null);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return selectList(statement,null);
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        try {
            return executor.query(ms, parameter, ms.getRowMapper());
        } catch (SQLException e) {
            log.error("查询异常：" + ms.getSqlSource().getSql(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return null;
    }

    @Override
    public int insert(String statement) {
        return 0;
    }

    @Override
    public int insert(String statement, Object parameter) {
        return 0;
    }

    @Override
    public int update(String statement) {
        return 0;
    }

    @Override
    public int update(String statement, Object parameter) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        try {
            return executor.update(ms, parameter);
        } catch (SQLException e) {
            log.error("更新异常：" + ms.getSqlSource().getSql(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int delete(String statement) {
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter) {
        return 0;
    }
}
