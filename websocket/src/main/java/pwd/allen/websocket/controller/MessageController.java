package pwd.allen.websocket.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.support.SendToMethodReturnValueHandler;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @author 门那粒沙
 * @create 2020-02-18 9:21
 **/
@Log4j2
@RestController
public class MessageController {

    /**
     * spring提供的消息发送工具，当支持stomp时可以从spring上下文中获取
     */
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * MessageMapping：用于监听指定路径的客户端消息，处理器为 {@link SimpAnnotationMethodMessageHandler}
     * SendTo：用于将服务端的消息发送给监听了该路径的客户端，处理器为 {@link SendToMethodReturnValueHandler}
     *
     * 判断mapping的代码：{@link org.springframework.messaging.handler.invocation.AbstractMethodMessageHandler#handleMessageInternal(Message, String)}
     *
     * @param message
     * @return
     */
    @MessageMapping("/broadcast")
    @SendTo("/topic/message")
    public Object message(String message, SimpMessageHeaderAccessor headerAccessor, @Headers Map headers) {
        String sender = headerAccessor.getUser().getName();
//        sender = headerAccessor.getFirstNativeHeader("user");

        log.info("sender：{}，接收到消息：{}", sender,  message);
        return String.format("%s发送了广播消息：%s", sender, message);
    }

    /**
     * 使用 SimpMessagingTemplate 服务端主动向客户端发送信息
     * @param message
     * @return
     */
    @GetMapping(value = "/message/{message}")
    public Object sendMessage(@PathVariable String message, @RequestParam(required = false) String destination) {
        if (destination == null) {
            destination = "/topic/message";
        }
        this.template.convertAndSend(destination, message);
        return "send：" + message;
    }

    @MessageMapping("/sendTo")
    public void chat(String message, @Header("user") String user, @Header("receiver") String receiver) {
        log.info("user：{},receiver：{},接收到消息：{}", user, receiver, message);
        // 方式一：每个客户端订阅/topic/{自己的名字}，然后服务器向该地址发送消息
//        this.template.convertAndSend("/topic/" + receiver, message);
        // 方式二：每个客户端订阅/user/topic/resp，然后服务器用convertAndSendToUser发送消息，，需要有配置Principal，否则无法根据user找到sessionId
        template.convertAndSendToUser(receiver, "/topic/resp", String.format("%s给你发送了消息：%s", receiver, message));
    }

    /**
     * 使用@SendToUser会写消息给发送人，客户端需要订阅/user/topic/resp
     * 这种方式是根据sessionId匹配，即发送给当前订阅/user/topic/resp的会话
     *
     * 相当于template.convertAndSendToUser(String user, String destination, Object payload)，如果要用这种方式，需要有配置Principal，否则无法根据user找到sessionId
     *
     * @see org.springframework.messaging.simp.user.UserDestinationMessageHandler#handleMessage(Message)
     *
     * 问题：
     *  1.@SendToUser("/resp")时无法接收到信息，猜想是需要指定前缀为broker前缀，否则没有消息代理处理
     *  2.除了自己外，其他人也收到了信息，看看是否自定义Principal，然后有不唯一的情况，例如user相同时，根据user获取sessionId会有多个，这些会话都会收到信息
     *
     * @param message
     * @param user
     * @return
     */
    @MessageMapping("/resp")
    @SendToUser("/topic/resp") //客户端需要订阅/user/topic/resp
    public String resp(@Payload String message, @Header("user") String user, Principal principal) {
        log.info("user：{},接收到消息：{}", principal.getName(), message);
        return String.format("【%s】%s", user, message);
    }
}
