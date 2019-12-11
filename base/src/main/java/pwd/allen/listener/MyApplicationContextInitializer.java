package pwd.allen.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 回调时机：ioc容器fresh之前
 *
 * 使用方式：
 * （1）配置在META-INF/spring.factories中
 *      org.springframework.context.ApplicationContextInitializer=\
 *      pwd.allen.listener.MyApplicationContextInitializer
 * （2）
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
