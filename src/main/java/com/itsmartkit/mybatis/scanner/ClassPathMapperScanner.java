package com.itsmartkit.mybatis.scanner;

import com.itsmartkit.mybatis.factory.bean.MapperFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Arrays;
import java.util.Set;

/**
 * @author cyj
 * @name ClassPathMapperScanner
 * @description TODO
 * @date 2020/3/6 11:31
 * Version 1.0
 */
@Slf4j
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends MapperFactoryBean> mapperFactoryBeanClass = MapperFactoryBean.class;

    private String sqlSessionFactoryBeanName = "sqlSessionFactoryBean";

    public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * @description //TODO
     * @author cyj
     * @date 2020/3/6 13:36
     * @param beanDefinitions
     * @return void
     * change notes
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            // 设置名称
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            // 设置 beanFactory
            definition.setBeanClass(mapperFactoryBeanClass);
            // 设置sqlSession
            definition.getPropertyValues().add("sqlSession",
                    new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
        }
    }


    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}