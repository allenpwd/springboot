package pwd.allen.base.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 作用：允许我们在 Spring 容器启动前，对 ApplicationContext 进行进一步的配置和处理
 *
 * 实现示例
 *  springboot整合nacos：NacosConfigApplicationContextInitializer
 *
 * 回调时机：ioc容器fresh之前
 *
 * 使用方式：
 * （1）配置在META-INF/spring.factories中
 *      org.springframework.context.ApplicationContextInitializer=\
 *      pwd.allen.listener.MyApplicationContextInitializer
 *
 * @author 门那粒沙
 * @create 2019-09-01 21:35
 **/
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("ApplicationContextInitializer.initialize..." + applicationContext);
    }
}
