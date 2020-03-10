package com.itsmartkit.mybatis.binding;

import com.itsmartkit.mybatis.annotations.Mapper;
import com.itsmartkit.mybatis.annotations.MapperScan;
import com.itsmartkit.mybatis.factory.bean.SqlSessionFactoryBean;
import com.itsmartkit.mybatis.scanner.ClassPathMapperScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ImportBeanDefinitionRegistrar
 * @Description TODO
 * @Author changyj
 * @Date 2020-03-05 23:18
 */
@Slf4j
public class MapperProxyBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 注入SqlSession
        BeanDefinitionBuilder builderSqlSession = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactoryBean.class);
        AbstractBeanDefinition sqlSessionBd = builderSqlSession.getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("sqlSessionFactoryBean", sqlSessionBd);

        // 扫描Mapper
        ClassPathMapperScanner scanner = new ClassPathMapperScanner(beanDefinitionRegistry);
        if (resourceLoader !=null ) {
            scanner.setResourceLoader(resourceLoader);
        }
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        scanner.scan(StringUtils.toStringArray(basePackages));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
