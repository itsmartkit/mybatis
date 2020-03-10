package com.itsmartkit.mybatis.mapping;

import com.itsmartkit.mybatis.session.Configuration;
import lombok.Getter;

/**
 * @ClassName StaticSqlSource
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 22:34
 */
@Getter
public class StaticSqlSource implements SqlSource {

    private final String sql;
    private final Configuration configuration;

    public StaticSqlSource(String sql, Configuration configuration) {
        this.sql = sql;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql() {
        return null;
    }
}
