package com.itsmartkit.mybatis.session;

import org.springframework.beans.factory.InitializingBean;

/**
 * @ClassName SqlSessionSupport
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-06 02:34
 */
public abstract class  SqlSessionSupport implements InitializingBean {

    private SqlSession sqlSession;

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.checkConfig();
    }

    public SqlSession getSqlSession() {
        return sqlSession;
    }

    protected void checkConfig() {
        // notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }
}
