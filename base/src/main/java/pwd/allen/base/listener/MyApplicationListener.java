package pwd.allen.base.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * TODO 没生效
 * @author pwdan
 * @create 2024-05-31 15:53
 **/
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        System.out.println("ApplicationListener.onApplicationEvent...监听ApplicationPreparedEvent");
    }
}
