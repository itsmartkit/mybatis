package com.itsmartkit.mybatis.mapping;

/**
 * @InterfaceName SqlSource
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 22:33
 */
public interface SqlSource {

    String getSql();

    BoundSql getBoundSql();

}
