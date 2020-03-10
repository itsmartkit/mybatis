package com.itsmartkit.mybatis.executor;

import com.itsmartkit.mybatis.mapping.MappedStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SimpleExecutor
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-07 02:22
 */
@Slf4j
public class SimpleExecutor implements Executor {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SimpleExecutor(NamedParameterJdbcTemplate jdbcTemplate){
         this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) throws SQLException {
        return jdbcTemplate.update(ms.getSqlSource().getSql(),buildParameterMap(ms,parameter));
    }

    @Override
    public  List query(MappedStatement ms, Object parameter, RowMapper rowMapper) throws SQLException {
        return jdbcTemplate.query(ms.getSqlSource().getSql(),
                buildParameterMap(ms,parameter), ms.getRowMapper());
    }

    private Map buildParameterMap(MappedStatement ms, Object parameter) {
        List<Map<String, Object>> parameterMaps = ms.getParameterMaps();
        Map params = new HashMap(parameterMaps.size());
        if (parameter.getClass().isArray() && ((Object[])parameter).length == parameterMaps.size()) {
            Object[] pms =  (Object[])parameter;
            if (pms.length == 1 && !isBaseTypeOrString(parameter)) {
                return BeanMap.create(pms[0]);
            }
            for (int i = 0; i < parameterMaps.size(); i++) {
                params.put(parameterMaps.get(i).get("name"), pms[i]);
            }
            return params;
        }
        if (isBaseTypeOrString(parameter)) {
            params.put(parameterMaps.get(0).get("name"), parameter);
        } else {
            return BeanMap.create(parameter);
        }
        return params;
    }

    public static boolean isBaseTypeOrString(Object object) {
        Class clazz = object.getClass();
        return clazz.isPrimitive() || clazz.equals(java.lang.String.class);
    }
}
