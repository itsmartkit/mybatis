package com.itsmartkit.mybatis.factory.bean;

import com.itsmartkit.mybatis.session.Configuration;
import com.itsmartkit.mybatis.session.DefaultSqlSession;
import com.itsmartkit.mybatis.session.SqlSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @ClassName SqlSessionFactoryBean
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 02:48
 */
@Slf4j
public class SqlSessionFactoryBean implements FactoryBean<SqlSession> {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public SqlSessionFactoryBean(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SqlSession getObject() throws Exception {
        return new DefaultSqlSession(Configuration.getInstance(), jdbcTemplate);
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSession.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
