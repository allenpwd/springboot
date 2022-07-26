package pwd.allen.websocket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwdan
 * @create 2022-05-30 9:58
 **/
public class WebSocketUtils {

    private static final Logger log = LoggerFactory.getLogger(WebSocketUtils.class);
    /**
     * 已连接的websocket
     */
    private static Map<String, WebSocketSession> onlineSession = new HashMap<>();

    /**
     * 添加用户
     *
     * @param session
     */
    public static void addSessoin(WebSocketSession session) {
        onlineSession.put(session.getId(), session);
        log.info("{}的用户连接websocket", session.getId());
    }

    /**
     * 移除用户
     *
     * @param session
     */
    public static void reduceSession(WebSocketSession session) {
        onlineSession.remove(session.getId());
        log.info("{}的用户断开websocket", session.getId());
    }

    /**
     * 开启监测
     * 本质是一监控一线程
     *
     * @param sessionId
     */
    public static void startMonitor(String sessionId) {
        WebSocketSession session = onlineSession.get(sessionId);
        String query = session.getUri().getQuery();
        new FileMonitor(session.getId(), decQuery(query));
    }

    /**
     * 关闭监控
     * session关闭，相应线程也会关闭
     *
     * @param sessionId
     */
    public static void endMonitor(String sessionId) {
        WebSocketSession session = onlineSession.get(sessionId);
        sendMessageTo(sessionId,"<error>ERROR 监控线程出现异常！</error>");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息给指定用户
     *
     * @param sessionId
     * @param message
     */
    public static void sendMessageTo(String sessionId, String message) {
        WebSocketSession session = onlineSession.get(sessionId);
        try {
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
            WebSocketUtils.endMonitor(sessionId);
        }
    }

    /**
     * session是否在线
     * 用于决定线程是否关闭
     *
     * @param sessionId
     * @return
     */
    public static boolean currentSessionAlive(String sessionId) {
        return onlineSession.containsKey(sessionId);
    }

    /**
     * 将query进行base64解密
     * @param query
     * @return
     */
    public static Map<String, String> decQuery(String query) {
        // query需要经过base64解密
        query = new String(Base64.getDecoder().decode(query.getBytes()));
        try {
            query = URLDecoder.decode(query, Charset.defaultCharset().name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String> mapParam = new HashMap<>();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] split = param.split("=");
                if (split.length == 2) {
                    mapParam.put(split[0], split[1]);
                }
            }
        }
        return mapParam;
    }
}
