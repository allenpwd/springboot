package pwd.allen.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 作用：监控各种事件
 * 需要配置在META-INF/spring.factories中
 *
 * @author 门那粒沙
 * @create 2019-09-01 21:38
 **/
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    /**
     * 要定义该构造函数，否则报错：java.lang.NoSuchMethodException: pwd.allen.listener.MySpringApplicationRunListener.<init>(org.springframework.boot.SpringApplication, [Ljava.lang.String;)
     * @param springApplication
     * @param args
     */
    public MySpringApplicationRunListener(SpringApplication springApplication, String[] args) {

    }

    /**
     * ioc容器还没创建之前
     */
    @Override
    public void starting() {
        System.out.println("SpringApplicationRunListener.starting...");
    }

    /**
     * 基础环境准备好之后
     * @param environment
     */
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("SpringApplicationRunListener.environmentPrepared...os.name=" + environment.getSystemProperties().get("os.name"));
    }


    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.started...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.running...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener.failed...");
    }
}
