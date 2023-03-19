package pwd.allen.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncAnnotationAdvisor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 属性：
 *  mode:代理方式：默认是PROXY
 *    - PROXY：采用Spring的动态代理（含JDK动态代理和CGLIB）
 *    - ASPECTJ：使用AspectJ静态代理方式，它能够解决同类内方法调用不走代理对象的问题，但是一般情况下都不建议这么去做，不要修改这个参数值
 *
 * 原理：
 *  引入了bean后置处理器 {@link org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor} 会将匹配的实例包装成代理对象
 *  其中增强器和切入点的创建是在构建Advisor的时候 {@link AsyncAnnotationAdvisor}
 *  增强处理委托给 {@link org.springframework.scheduling.annotation.AnnotationAsyncExecutionInterceptor}
 *  增强处理：
 *      获取异步处理器的逻辑在 {@link AsyncExecutionAspectSupport#determineAsyncExecutor(java.lang.reflect.Method)}，可通过@Async注解指定，否则取默认的，方法与处理器的映射关系会缓存在map中
 *      默认的Executor可通过注入{@link org.springframework.scheduling.annotation.AsyncConfigurerSupport}实现自定义
 *      没有指定的话就走spring自己的 {@link AsyncExecutionAspectSupport#getDefaultExecutor(org.springframework.beans.factory.BeanFactory)}
 *
 * 相关配置属性 {@link org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration}
 *
 * @author allen
 * @create 2023-03-19 10:31
 **/
@EnableAsync
@Configuration
public class AsyncConfig {

    /**
     * 将Executor包装成TtlExecutor使之支持线程池的线程变量共享，如果不包装的话，可能获取的共享变量是之前的
     * 沿用spring task的线程池配置 {@link org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Pool}
     *
     * 问题：异步处理器获取到了scheduler的那个
     * 原因：开启@EnableScheduling之后会自动装配一个ThreadPoolTaskScheduler，是TaskExecutor类型，默认处理器的逻辑是先判断是否有唯一的TaskExecutor
     * 自己定义的这个并不是TaskExecutor类型的，所以scheduler会被优先获取到
     * 解决方法：实现AsyncConfigurerSupport自定义executor，或者@Async的value属性指定ttlExecutor
     *
     * @param builder
     * @return
     */
    @Bean
    public Executor ttlExecutor(TaskExecutorBuilder builder) {
        ThreadPoolTaskExecutor executor = builder.build();
        // 如果包装成TtlExecutor，需要手动初始化
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }
}
