package com.itsmartkit.mybatis.binding;

import com.itsmartkit.mybatis.mapping.MappedStatement;
import com.itsmartkit.mybatis.mapping.SqlCommandType;
import com.itsmartkit.mybatis.session.Configuration;
import com.itsmartkit.mybatis.session.SqlSession;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MapperMethod
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 22:27
 */
public class MapperMethod {

    private final SqlCommand command;

    private final MethodSignature methodSignature;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.methodSignature = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        switch (command.type) {
            case INSERT:
                result = sqlSession.insert(command.getName(), args);
                break;
            case UPDATE:
                result = sqlSession.update(command.getName(), args);
                break;
            case SELECT:
                if (methodSignature.isReturnsVoid()) {
                    return null;
                } else if(methodSignature.isReturnsMap()) {
                    result = sqlSession.selectMap(command.getName(),args,null);
                } else if (methodSignature.isReturnsMany()) {
                    result = sqlSession.selectList(command.getName(), args);
                } else {
                    result = sqlSession.selectOne(command.getName(), args);
                }
                break;
            case DELETE:
                result = sqlSession.delete(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    @Getter
    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration config, Class<?> mapperInterface, Method method) {
            MappedStatement mappedStatement = config.getMappedStatement(mapperInterface.getName() + "." + method.getName());
            this.name = mappedStatement.getId();
            this.type = mappedStatement.getCommandType();
        }
    }

    @Getter
    public static class MethodSignature {
        private final boolean returnsMany;
        private final boolean returnsMap;
        private final boolean returnsVoid;
        private final Class<?> returnType;
//        private final String mapKey;
        public MethodSignature(Configuration config, Class<?> mapperInterface, Method method) {
            MappedStatement mappedStatement = config.getMappedStatement(mapperInterface.getName() + "." + method.getName());
            this.returnType = mappedStatement.getReturnType();
            this.returnsVoid = void.class.equals(this.returnType);
            this.returnsMany = this.returnType.isArray() || this.returnType.equals(List.class);
            this.returnsMap = this.returnType.isAssignableFrom(Map.class);
        }
    }
}

