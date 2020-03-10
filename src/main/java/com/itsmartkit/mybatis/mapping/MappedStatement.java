package com.itsmartkit.mybatis.mapping;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

/**
 * @author cyj
 * @name MappedStatement
 * @description TODO
 * @date 2020/3/6 15:39
 * Version 1.0
 */
@Getter
@Setter
public class MappedStatement {

    private String id;

    private  SqlCommandType commandType;

    private SqlSource sqlSource;

    private List<Map<String, Object>> parameterMaps;

    private Class<?> returnType;

    private RowMapper<?> rowMapper = new BeanPropertyRowMapper<>();

    public MappedStatement(String id, SqlCommandType commandType, SqlSource sqlSource) {
        this.id = id;
        this.commandType = commandType;
        this.sqlSource = sqlSource;
    }
}