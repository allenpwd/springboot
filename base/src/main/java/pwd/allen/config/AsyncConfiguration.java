package pwd.allen.config;

import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author 门那粒沙
 * @create 2021-01-24 10:08
 **/
@EnableAsync
@Configuration
public class AsyncConfiguration {

    /**
     * 将Executor包装成TtlExecutor使之支持线程池的线程变量共享，如果不包装的话，可能获取的共享变量是之前的
     *
     * 默认Executor配置可以参考：org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Pool
     *
     * @param builder
     * @return
     */
    @Bean(name = { TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME,
            AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME })
    @ConditionalOnMissingBean(Executor.class)
    public Executor applicationTaskExecutor(TaskExecutorBuilder builder) {
        ThreadPoolTaskExecutor executor = builder.build();
        // 如果包装成TtlExecutor，需要手动初始化
        executor.initialize();
        return TtlExecutors.getTtlExecutor(executor);
    }
}
