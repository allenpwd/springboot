package pwd.allen.base.listener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 *
 * {@link org.springframework.boot.context.event.ApplicationStartedEvent}
 * {@link org.springframework.boot.context.event.ApplicationReadyEvent}
 * {@link org.springframework.boot.context.event.ApplicationPreparedEvent} TODO 这个监控不到
 *
 * @author pwdan
 * @create 2024-05-31 15:53
 **/
@Component
public class MyApplicationListener implements ApplicationListener<SpringApplicationEvent> {
    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        System.out.println(String.format("ApplicationListener.onApplicationEvent...监听到事件:%s", event.getClass().getName()));
    }
}
