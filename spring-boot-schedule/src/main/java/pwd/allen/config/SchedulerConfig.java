package pwd.allen.config;

import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * 相关配置属性 {@link org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration}
 * @author allen
 * @create 2023-03-19 10:31
 **/
@EnableScheduling
@Configuration
public class SchedulerConfig {

    /**
     * 换成自定义的CustomTaskScheduler，拓展停止任务的功能
     * 也可以引入spring-boot-starter-aop模块然后asject来做
     * @param builder
     * @return
     */
    @Bean
    public CustomTaskScheduler taskScheduler(TaskSchedulerBuilder builder) {
        return builder.configure(new CustomTaskScheduler());
    }
}
