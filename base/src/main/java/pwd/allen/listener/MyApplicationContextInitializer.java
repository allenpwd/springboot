package pwd.allen.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ioc容器fresh之前做处理
 * 需要配置在META-INF/spring.factories中
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
