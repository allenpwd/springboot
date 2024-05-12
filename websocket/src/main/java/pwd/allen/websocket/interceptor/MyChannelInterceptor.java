package pwd.allen.websocket.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 自定义{@link org.springframework.messaging.support.ChannelInterceptor}，实现断开连接的处理
 *
 * @author 门那粒沙
 * @create 2020-02-18 19:57
 */
//@Component
@Log4j2
public class MyChannelInterceptor implements ChannelInterceptor {

    /**
     * 记录在线用户
     */
    public CopyOnWriteArraySet<String> set_users = new CopyOnWriteArraySet<>();

    /**
     * 不加@Lazy会报错，循环注入
     */
    @Lazy
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        String user = null;
        String sessionId = accessor.getSessionId();

        user = accessor.getFirstNativeHeader("user");
        if (StrUtil.isEmpty(user)) {
            // 这个好像时security认证相关的 TODO
            Principal principal = accessor.getUser();
            if (principal == null) {
                principal = SimpMessageHeaderAccessor.getUser(message.getHeaders());
            }
            if (principal != null && !StrUtil.isEmpty(principal.getName())) {
                user = principal.getName();
            } else {
                user = sessionId;
            }
        }
        // 广播通知大家
        if (template != null) {
            if (StompCommand.CONNECT.equals(command)) {
                set_users.add(user);
                template.convertAndSend("/topic/message", String.format("【%s】用户%s建立连接", sessionId, user));
            } else if (StompCommand.DISCONNECT.equals(command)) {
                //用户已经断开连接
                set_users.remove(user);
                template.convertAndSend("/topic/message", String.format("【%s】用户%s断开连接", sessionId, user));
                log.debug(MessageFormat.format("用户{0}的WebSocket连接已经断开", user));
            }
        }
    }

}
