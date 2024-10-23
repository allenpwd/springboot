package pwd.allen.base.listener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * 原理：META-INF/spring.factories下配置了SpringApplicationRunListener的实现类 {@link org.springframework.boot.context.event.EventPublishingRunListener}
 * 它主要负责在启动过程的不同阶段发布对应的事件。EventPublishingRunListener中有个initialMulticaster广播器，将第二步中获得的所有监听器都注册进来。然后如果有对应的事件，就会推送给他们。
 *
 * 事件发布顺序：
 * {@link org.springframework.boot.context.event.ApplicationStartingEvent}
 * {@link org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent}
 * {@link org.springframework.boot.context.event.ApplicationContextInitializedEvent}
 * {@link org.springframework.boot.context.event.ApplicationPreparedEvent}
 * {@link org.springframework.boot.context.event.ApplicationStartedEvent}
 * {@link org.springframework.boot.context.event.ApplicationReadyEvent}
 *
 * 其中通过@Component注入自定义的ApplicationListener、或者使用@EventListener注解方式注册监听器的方式只能监听到ApplicationStartedEvent及之后的事件。
 *
 * 如果要让自定义的ApplicationListener能监听到所有事件，可以在META-INF/spring.factories中声明：
 * org.springframework.context.ApplicationListener=\
 * pwd.allen.base.listener.MyApplicationListener
 *
 * @author pwdan
 * @create 2024-05-31 15:53
 **/
@Component
public class MyApplicationListener implements ApplicationListener<SpringApplicationEvent> {
    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        System.out.println(String.format("MyApplicationListener.onApplicationEvent...监听到事件:%s", event.getClass().getName()));
    }
}
