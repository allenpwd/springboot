package pwd.allen.websocket.interceptor;

import com.sun.net.httpserver.HttpPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

/**
 * 自定义Principal TODO
 * @author allen
 * @create 2022-08-07 22:42
 **/
@Component
public class StompHandshakeHander extends DefaultHandshakeHandler {

    /**
     * 这里可以在握手时，设置principal 或者 额外的属性
     *
     * principal 可以在后面的消息处理中通过：StompHeaderAccessor.wrap(message).getUser()获取
     *
     * 配置了principal，{@link SimpMessagingTemplate#convertAndSendToUser(String, String, Object)}才能正常发送消息给指定用户
     *
     * @param request the handshake request
     * @param wsHandler the WebSocket handler that will handle messages
     * @param attributes handshake attributes to pass to the WebSocket session
     * @return
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 这里设置的属性的获取方式：StompHeaderAccessor.wrap(message).getSessionAttributes().get("date");
        attributes.put("date", new Date());
        Principal principal = super.determineUser(request, wsHandler, attributes);
        if (principal == null) {
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                principal = new HttpPrincipal(servletRequest.getServletRequest().getParameter("username"), "realm");
            }
        }
        System.out.println("StompHandshakeHander.determineUser:" + principal);
        return principal;
    }
}
