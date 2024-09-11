package pwd.allen.rocketmq.nativedemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * 死信队列是死信Topic下分区数唯一的单独队列。如果产生了死信消息，那对应的ConsumerGroup的死信Topic名称为%DLQ%ConsumerGroupName，死信队列的消息将不会再被消费。
 * 我们也可以去监听死信队列，然后进行自己的业务上的逻辑
 *
 * @author pwdan
 * @create 2024-09-06 9:58
 **/
@Slf4j
public class ConsumeTest {

    private String namesrvAddr = "192.168.118.102:9876";
    private String group = "test-consumer-group";
    private String topic = "test-topic";


    /**
     * 单线程模式消费
     * TODO 监控不到历史信息
     *
     * @throws Exception
     */
    @Test
    public void consumerOrderly() throws Exception {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        //连接nameSrv
        consumer.setNamesrvAddr(namesrvAddr);
        //订阅一个主题，第二个入参是用来过滤信息的，默认是*,支持"tagA || tagB || tagC" 这样或者的写法
        consumer.subscribe("test-topic","*");

        // 设置一个监听器 (一直监听，异步回调)
        // MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext consumeConcurrentlyContext) {
                for (int i = 0; i < msgs.size(); i++) {
                    log.info("消息{}:{}", i, new String(msgs.get(i).getBody()));
                }
                //返回成功（SUCCESS） ,消息从队列中弹出
                //RECONSUME,失败，消息重新回到队列，过一会重新投递供当前消费者或其他消费者
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动
        consumer.start();

        //挂起当前的jvm
        System.in.read();
    }

    /**
     * 多线程模式消费
     *
     * @throws Exception
     */
    @Test
    public void consumerConcurrently() throws Exception {
        //创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        //连接nameSrv
        consumer.setNamesrvAddr(namesrvAddr);
        //订阅一个主题  * 代表订阅这个主题中所有信息
        consumer.subscribe("test-topic","*");

        consumer.setMaxReconsumeTimes(3);

        // 设置一个监听器 (一直监听，异步回调)
        // MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (int i = 0; i < msgs.size(); i++) {
                    log.info("消息{}:{}", i, new String(msgs.get(i).getBody()));
                }
                //返回成功（CONSUME_SUCCESS） ,消息从队列中弹出
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                //ConsumeConcurrentlyStatus.RECONSUME_LATER,失败，消息重新回到队列，过一会重新投递供当前消费者或其他消费者
                // 如果重试次数超过MaxReconsumeTimes，则消息会被放入死信队列
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        //启动
        consumer.start();

        //挂起当前的jvm
        System.in.read();
    }
}
