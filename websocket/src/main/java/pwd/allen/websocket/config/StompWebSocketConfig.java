package pwd.allen.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import pwd.allen.websocket.interceptor.MyChannelInterceptor;
import pwd.allen.websocket.interceptor.StompHandshakeHander;

import java.util.List;

/**
 * 使用STOMP子协议作为cs通信的通用格式
 * 特点：
 *  多方使用可拓展性强（通过订阅广播自定义）
 *  STOMP协议为浏览器和server间的通信增加适当的消息语义
 *
 * @author 门那粒沙
 * @create 2020-02-15 23:14
 **/
@Configuration
@EnableWebSocketMessageBroker// 注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private StompHandshakeHander stompHandshakeHander;

    /**
     * 注册端点，STOMP客户端连接的地址
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/broker")
                // 配置跨域
//                .setAllowedOrigins("*")   // 这个会报错：When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header. To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead.
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(stompHandshakeHander)
                .withSockJS();//启动SockJS,以便在WebSocket不可用时可以使用备用传输
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //启用一个简单的基于内存的消息代理，指定服务端广播消息的路径前缀
        //也可以用成其他的传统信息代理，比如rabbitmq activeMq TODO
        registry.enableSimpleBroker("/topic");

        //指定服务端处理WebSocket消息的前缀是/app
        //如果客户端向/app/hello这个地址发送消息，那么服务端通过@MessageMapping(“/hello”)这个注解来接收并处理消息
        registry.setApplicationDestinationPrefixes("/app");

        //用户名称前缀，默认为/user/，当客户段订阅/user开头，spring会解析成/user/{userId}/...
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 添加拦截器，可以用于处理客户端发送的消息，例如在消息到达服务器之前进行身份验证、日志记录等操作
        registration.interceptors(myChannelInterceptor());
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        //理论上Web Socket消息的大小几乎是无限的,但实际上Websocket服务器会施加限制
        // 如 Tomcat上的5KB和Jety上的64KB。因此, STOMP客户端(如 Javascript webstomp-client等)在16KB边界
        //处分割较大的 STOMP消息、并将它们作为多个 Websocket消息发送,需要服务器进行缓冲和重新组装。
        registration.setSendBufferSizeLimit(8888)//配置向客户端发送消息时可以缓存多少数据
                .setSendTimeLimit(1000 * 10)//配置向客户端发送消息的时间限制，单位毫秒
                .setMessageSizeLimit(1024 * 128);//配置Stomp最大容量
    }

    /**
     * 添加消息转换器
     * @param messageConverters the converters to configure (initially an empty list)
     * @return whether to also add default converter or not
     */
    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
    }

    @Bean
    public MyChannelInterceptor myChannelInterceptor() {
        return new MyChannelInterceptor();
    }
}

