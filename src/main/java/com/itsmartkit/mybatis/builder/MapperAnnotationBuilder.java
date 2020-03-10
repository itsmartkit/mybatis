package com.itsmartkit.mybatis.builder;
import com.itsmartkit.mybatis.annotations.*;
import com.itsmartkit.mybatis.mapping.MappedStatement;
import com.itsmartkit.mybatis.mapping.SqlCommandType;
import com.itsmartkit.mybatis.mapping.SqlSource;
import com.itsmartkit.mybatis.mapping.StaticSqlSource;
import com.itsmartkit.mybatis.session.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author cyj
 * @name MapperAnnotationBuilder
 * @description TODO
 * @date 2020/3/6 14:54
 * Version 1.0
 */
@Slf4j
public class MapperAnnotationBuilder {
    private final Configuration configuration;
    private final Class<?> type;

    private final static Set<Class<? extends Annotation>> types = new HashSet<>();

    static {
        types.add(Insert.class);
        types.add(Select.class);
        types.add(Update.class);
        types.add(Delete.class);
    }

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        this.configuration = configuration;
        this.type = type;
    }

    public void parse() {

        Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            String id = type.getName() + "." + method.getName();
            Annotation annotation = getMethodAnnotation(method);
            if (annotation != null) {
                SqlCommandType commandType = SqlCommandType.NONE;
                String sql = "";
                if (annotation.annotationType() == Insert.class) {
                    commandType = SqlCommandType.INSERT;
                    Insert insert = (Insert) annotation;
                    sql = insert.value();
                }
                if (annotation.annotationType() == Update.class) {
                    commandType = SqlCommandType.UPDATE;
                    Update update = (Update) annotation;
                    sql = update.value();
                }
                if (annotation.annotationType() == Select.class) {
                    commandType = SqlCommandType.SELECT;
                    Select select = (Select) annotation;
                    sql = select.value();
                }
                if (annotation.annotationType() == Delete.class) {
                    commandType = SqlCommandType.DELETE;
                    Delete delete = (Delete) annotation;
                    sql = delete.value();
                }

                SqlSource sqlSource = new StaticSqlSource(sql, configuration);

                MappedStatement ms = new MappedStatement(id, commandType, sqlSource);
                // param
                Parameter[] parameters = method.getParameters();
                List<Map<String, Object>> params = new ArrayList<>(parameters.length);
                for (Parameter p : parameters) {
                    Param paraAnno = p.getAnnotation(Param.class);
                    Map<String, Object> map = new HashMap<>();
                    if (paraAnno != null) {
                        map.put("name", paraAnno.value());
                    } else {
                        map.put("name", p.getName());
                    }
                    map.put("type", p.getType());
                    params.add(map);
                }
                ms.setParameterMaps(params);
                //
                ms.setReturnType(method.getReturnType());

                Type resultType = method.getGenericReturnType();
                if (resultType instanceof ParameterizedType && ((ParameterizedType) resultType).getRawType() == List.class) {
                    Type[] resultArgType = ParameterizedType.class.cast(resultType).getActualTypeArguments();
                    if (resultArgType!=null && resultArgType.length > 0) {
                        resultType = resultArgType[0];
                    }
                }
                if (resultType != null) {
                    try {
                        ms.setRowMapper(new BeanPropertyRowMapper<>(Class.forName(resultType.getTypeName())));
                    } catch (ClassNotFoundException e) {
                        log.error("ClassNotFoundException" ,e);
                    }
                }
                if (!configuration.hasMappedStatement(ms)) {
                    configuration.addMappedStatement(ms);
                } else {
                    throw new RuntimeException("The MappedStatement '" + id + "' already exists!");
                }
            }

        }

    }


    public Annotation getMethodAnnotation(Method method) {
        for(Class klass : types) {
            Annotation annotation = method.getAnnotation(klass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

}