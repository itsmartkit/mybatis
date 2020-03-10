package com.itsmartkit.mybatis;

import com.itsmartkit.mybatis.annotations.MapperScan;
import com.itsmartkit.mybatis.session.DefaultSqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan(basePackages = {"com.itsmartkit.mybatis"})
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
