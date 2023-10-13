package pwd.allen.websocket.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.support.SendToMethodReturnValueHandler;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 门那粒沙
 * @create 2020-02-18 9:21
 **/
@Log4j2
@RestController
public class MessageController {

    /**
     * spring提供的推送方式，当支持stomp时可以从spring上下文中获取
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
        String sender = headerAccessor.getFirstNativeHeader("sender");
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
        if (destination == null) destination = "/topic/message";
        this.template.convertAndSend(destination, message);
        return "send：" + message;
    }

    @MessageMapping("/sendTo")
    public void chat(String message, @Header("sender") String sender, @Header("receiver") String receiver) {
        log.info("sender：{},receiver：{},接收到消息：{}", sender, receiver, message);
        this.template.convertAndSend("/topic/" + receiver, message);
    }

    /**
     * 使用@SendToUser会写消息给发送人，客户端需要订阅/user/topic/resp
     *
     * 相当于template.convertAndSendToUser(String user, String destination, Object payload)
     *
     * @see org.springframework.messaging.simp.user.UserDestinationMessageHandler
     *
     * @param message
     * @param sender
     * @return
     */
    @MessageMapping("/resp")
    @SendToUser("/topic/resp") //客户端需要订阅/user/topic/resp
    public String  resp(String message, @Header("sender") String sender) {
        log.info("sender：{},接收到消息：{}", sender, message);
        return String.format("【%s】%s", sender, message);
    }
}
