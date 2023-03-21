package pwd.allen.base.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 作用：监控各种事件
 * 需要配置在META-INF/spring.factories中配置
 *   org.springframework.boot.SpringApplicationRunListener=\
 *    pwd.allen.listener.MySpringApplicationRunListener
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
     * 在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作
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


    /**
     * ApplicationContext构建完成
     * @param context
     */
    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.contextPrepared...");
    }

    /**
     * ApplicationContext完成加载，但没有被刷新前
     * @param context
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.contextLoaded...");
    }

    /**
     * 在ApplicationContext刷新并启动后，CommandLineRunners和ApplicationRunner未被调用前
     * @param context
     */
    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.started...");
    }

    /**
     * 在run()方法执行完成前该方法被调用
     * @param context
     */
    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringApplicationRunListener.running...");
    }

    /**
     * 当应用运行出错时该方法被调用
     * @param context
     * @param exception
     */
    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringApplicationRunListener.failed...");
    }
}
