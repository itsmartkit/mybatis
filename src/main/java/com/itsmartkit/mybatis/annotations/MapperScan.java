package com.itsmartkit.mybatis.annotations;

import com.itsmartkit.mybatis.binding.MapperProxyBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ClassName MapperScan
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 23:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(MapperProxyBeanDefinitionRegistrar.class)
public @interface MapperScan {
    String[] basePackages() default "";
}
