package pwd.allen.websocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import pwd.allen.websocket.util.WebSocketUtils;

/**
 * @author pwdan
 * @create 2022-05-30 9:57
 **/
public class SocketEventHandler extends AbstractWebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(SocketEventHandler.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("进行连接");
        WebSocketUtils.addSessoin(session);
        WebSocketUtils.startMonitor(session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("关闭连接");
        WebSocketUtils.reduceSession(session);
    }
}
