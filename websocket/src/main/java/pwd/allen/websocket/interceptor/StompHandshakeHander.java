package pwd.allen.websocket.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Principal TODO
 * @author allen
 * @create 2022-08-07 22:42
 **/
@Component
public class StompHandshakeHander extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return super.determineUser(request, wsHandler, attributes);
    }
}
