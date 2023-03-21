package pwd.allen.base.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.DefaultLifecycleProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * bean生命周期的几个回调方式的顺序：
 *   初始化（在该bean创建并且所有必要依赖配置完成后）：
 *      注解@PostConstruct标注的方法
 *      接口InitializingBean的实现方法afterPropertiesSet
 *      init-method属性指定的方法
 *   所有单bean初始化完之后：
 *      接口SmartInitializingSingleton的实现方法afterSingletonsInstantiated
 *      SmartLifeCycle.start
 *   销毁：
 *      注解@PreDestroy标注的方法
 *      接口DisposableBean的实现方法destroy
 * @author allen
 * @create 2023-03-20 8:10
 **/
@Slf4j
@Data
public class MyBean implements InitializingBean, DisposableBean, SmartInitializingSingleton, SmartLifecycle {

    @Value("${server.port}")
    private Integer port;

    /**
     * 执行时机：初始化该bean的时候
     *  -》{@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *  -》{@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
     *  -》{@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)} bean后置处理器，实现了PriorityOrdered接口，有高优先级
     */
    @PostConstruct
    public void postConstruct() {
        log.info("【{}】@PostConstruct：port={}", this.getClass().getName(), port);
    }

    /**
     *
     * 执行时机：初始化该bean的时候
     *  -》{@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *  -》{@link AbstractAutowireCapableBeanFactory#invokeInitMethods(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *
     * 不推荐，因为代码与接口耦合
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【{}】InitializingBean.afterPropertiesSet：port={}", this.getClass().getName(), port);
    }

    /**
     * 使用方式：通过@Bean的initMethod属性指定
     *
     * 执行时机：初始化该bean的时候
     *  -》{@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *  -》{@link AbstractAutowireCapableBeanFactory#invokeInitMethods(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *  -》{@link AbstractAutowireCapableBeanFactory#invokeCustomInitMethod(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
     *
     * @throws Exception
     */
    public void initMethod() {
        log.info("【{}】initMethod：port={}", this.getClass().getName(), port);
    }

    /**
     * 执行时机：在所有单Bean创建完成之后执行
     *  -》{@link DefaultListableBeanFactory#preInstantiateSingletons()} 先实例化所有需要的bean，然后再遍历一遍，如果有SmartInitializingSingleton则调用afterSingletonsInstantiated
     *  -》
     */
    @Override
    public void afterSingletonsInstantiated() {
        log.info("【{}】SmartInitializingSingleton.afterSingletonsInstantiated：port={}", this.getClass().getName(), port);
    }

    /**
     * 执行时机：在所有单Bean创建完成之后，并且该bean的isRunning返回false的时候，会回调
     *  -》{@link ServletWebServerApplicationContext#finishRefresh()}
     *  -》{@link DefaultLifecycleProcessor#doStart(java.util.Map, java.lang.String, boolean)}
     */
    @Override
    public void start() {
        log.info("【{}】SmartLifecycle.start：port={}", this.getClass().getName(), port);
    }


    @PreDestroy
    public void PreDestroy() {
        log.info("【{}】@PreDestroy：port={}", this.getClass().getName(), port);
    }

    @Override
    public void destroy() throws Exception {
        log.info("【{}】DisposableBean.destroy：port={}", this.getClass().getName(), port);
    }

    @Override
    public void stop() {
        log.info("【{}】SmartLifecycle.stop：port={}", this.getClass().getName(), port);
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
