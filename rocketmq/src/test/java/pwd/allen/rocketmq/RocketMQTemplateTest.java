package pwd.allen.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pwd.allen.rocketmq.constant.MQGroup;
import pwd.allen.rocketmq.constant.MQTags;
import pwd.allen.rocketmq.constant.MQTopic;

@Slf4j
@SpringBootTest(classes = RocketMqMain.class)
public class RocketMQTemplateTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void convertAndSend() throws InterruptedException {
        template.convertAndSend(MQTopic.SPRINGBOOT_TOPIC.getTopic(), "convertAndSend");
        Thread.sleep(1000L);
    }

    @Test
    public void sendMessageInTransaction() {
        Message message = MessageBuilder.withPayload("sendMessageInTransaction").build();
        TransactionSendResult result = template.sendMessageInTransaction(MQGroup.SPRING_BOOT_PRODUCER_GROUP.getGroup(), MQTopic.SPRINGBOOT_TOPIC.getTopic(), message, null);
        log.info("【发送状态】：{}", result.getLocalTransactionState());
    }

}
