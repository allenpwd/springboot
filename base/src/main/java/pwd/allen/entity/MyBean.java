package pwd.allen.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.context.SmartLifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * bean生命周期的几个回调方式的顺序：
 *   初始化（在该bean创建并且所有必要依赖配置完成后）：
 *      注解@PostConstruct标注的方法【applyBeanPostProcessorsBeforeInitialization-》CommonAnnotationBeanPostProcessor.postProcessBeforeInitialization】
 *      接口InitializingBean的实现方法afterPropertiesSet（不推荐，因为代码与接口耦合）【AbstractAutowireCapableBeanFactory#invokeInitMethods】
 *      init-method属性指定的方法【AbstractAutowireCapableBeanFactory#invokeInitMethods】
 *      接口SmartInitializingSingleton的实现方法afterSingletonsInstantiated（不推荐，因为代码与接口耦合），单例情况下【preInstantiateSingletons】
 *
 *      SmartLifeCycle.start(所有bean准备好之后)【finishRefresh-》 DefaultLifecycleProcessor#startBeans 】
 *   销毁：
 *      注解@PreDestroy标注的方法
 *      接口DisposableBean的实现方法destroy（不推荐，因为代码与接口耦合）
 * @author allen
 * @create 2023-03-20 8:10
 **/
@Slf4j
@Data
public class MyBean implements InitializingBean, DisposableBean, SmartInitializingSingleton, SmartLifecycle {

    @Value("${server.port}")
    private Integer port;

    /**
     * 执行时机：
     *  -》{@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
     *  -》{@link InitDestroyAnnotationBeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)} bean后置处理器，实现了PriorityOrdered接口，有高优先级
     */
    @PostConstruct
    public void postConstruct() {
        log.info("【{}】@PostConstruct：port={}", this.getClass().getName(), port);
    }

    /**
     *
     * 不推荐，因为代码与接口耦合
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("【{}】InitializingBean.afterPropertiesSet：port={}", this.getClass().getName(), port);
    }

    public void initMethod() {
        log.info("【{}】initMethod：port={}", this.getClass().getName(), port);
    }

    /**
     * {@link SmartInitializingSingleton} 的接口实现，在所有单Bean创建完成之后执行
     */
    @Override
    public void afterSingletonsInstantiated() {
        log.info("【{}】SmartInitializingSingleton.afterSingletonsInstantiated：port={}", this.getClass().getName(), port);
    }

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
