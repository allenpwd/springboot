package pwd.allen.base.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * BeanDefinitionRegistryPostProcessor接口在BeanFactoryPostProcessor基础上额外定义的方法
 *
 * @author pwd
 * @create 2018-12-01 16:36
 **/
@Slf4j
@Component
public class MyBeanDefinitionRegistryPostProcessor extends MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    /**
     * 执行时机：所有bean定义信息将要被加载、bean实例未被创建（在postProcessBeanFactory之前）
     *  -》{@link AbstractApplicationContext#invokeBeanFactoryPostProcessors}
     *
     * @param registry Bean定义信息的保存中心，以后BeanFactory就是按照这些信息创建Bean实例的
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("【BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry】bean数量：{}", registry.getBeanDefinitionCount());
    }
}
