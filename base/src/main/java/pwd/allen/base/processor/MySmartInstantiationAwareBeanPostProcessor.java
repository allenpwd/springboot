package pwd.allen.base.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * 拓展了MyInstantiationAwareBeanPostProcessor，添加了一些新的方法来增强bean实例化的过程。
 * 注意：该接口是一个特殊用途的接口，主要用于框架内的内部使用。一般我们使用它，不然很容易出问题
 * @author pwdan
 * @create 2023-03-22 11:35
 **/
@Slf4j
//@Component
public class MySmartInstantiationAwareBeanPostProcessor extends MyInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    /**
     * 根据前置条件可以预测bean的类型，这个方法返回的类型将用于创建bean的实例
     *
     * 执行时机：遍历bean定义
     *  -》{@link ServletWebServerApplicationContext#onRefresh}
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
        return beanClass;
    }

    /**
     * 在创建bean实例之前，确定候选的构造器。这个方法允许你自定义bean的构造过程，例如选择重载的构造器或者修改构造器的可见性等
     *
     * 执行时机：实例化bean之前
     *
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 可以在bean实例化的过程中提前暴露一个bean的代理对象，而不是返回真正的bean实例。这种方式通常用于解决循环依赖的问题
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return null;
    }
}
