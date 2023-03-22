package pwd.allen.base.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 *
 * 作用：在BeanFactory标准初始化之后修改应用程序上下文的内部bean定义，例如：
 *  修改、新增bean定义
 *  利用外部属性文件配置bean参数
 *  实现BeanReference替换
 *
 * spring实现实例
 *  -》{@link org.springframework.context.support.PropertySourcesPlaceholderConfigurer}
 *      将外部的配置文件中的key-value属性值注入到bean中
 *  -》{@link org.springframework.context.annotation.ConfigurationClassPostProcessor}
 *      用于将标注有@Configuration等注解的JavaConfig配置类转换成对应的BeanDefinition，并注册到容器中
 *  -》{@link }
 *
 * @author pwdan
 * @create 2023-03-22 15:58
 **/
@Slf4j
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * BeanFactoryPostProcessor接口的方法
     * 执行时机：所有bean定义已经保存并加载到beanFactory、但bean实例还未创建
     *  -》{@link AbstractApplicationContext#invokeBeanFactoryPostProcessors}
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        int beanDefinitionCount = beanFactory.getBeanDefinitionCount();
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        log.info("【BeanFactoryPostProcessor.postProcessBeanFactory】bean数量:{},beanDefinitionNames:{}", beanDefinitionCount, beanDefinitionNames);
    }
}
