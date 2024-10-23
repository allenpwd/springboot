package pwd.allen.base.listener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 使用 @EventListener 注解来监听这些事件
 *
 * @author pwdan
 * @create 2024-05-31 15:53
 **/
@Component
public class MyApplicationListener2 {

    @EventListener
    public void onApplicationEvent(SpringApplicationEvent event) {
        System.out.println(String.format("MyApplicationListener2.onApplicationEvent...监听到事件:%s", event.getClass().getName()));
    }
}
