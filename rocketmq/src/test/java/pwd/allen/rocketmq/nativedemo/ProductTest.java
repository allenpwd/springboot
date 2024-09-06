package pwd.allen.rocketmq.nativedemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author pwdan
 * @create 2024-09-06 9:58
 **/
@Slf4j
public class ProductTest {

    private String namesrvAddr = "192.168.118.102:9876";
    private String producerGroup = "test-producer-group";
    private String topic = "test-topic";

    private DefaultMQProducer producer;

    @BeforeEach
    public void before() throws MQClientException {
        //创建一个生产者，制定一个组名
        producer = new DefaultMQProducer(producerGroup);
        //连接namesrv
        producer.setNamesrvAddr(namesrvAddr);
        //启动
        producer.start();
    }

    @AfterEach
    public void after() {
        //关闭生产者
        producer.shutdown();
    }

    /**
     * 同步发送消息
     * 等待直到broker接收到消息后返回确认
     * 在mq集群中，要等到所有的从机都复制了消息以后才会返回
     * 适用于响应要求不高的重要消息
     *
     * @throws MQClientException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    public void syncSend() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        //创建信息
        Message message = new Message(topic, "我是一个同步的信息".getBytes());
        //发送信息
        SendResult sendResult = producer.send(message);
        log.info("sendResult:{}", sendResult.getSendStatus().toString());
    }

    /**
     * 异步发送消息
     * 适用于响应时间要求较高的业务场景
     *
     * @throws MQClientException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    public void asyncSend() throws MQClientException, RemotingException, InterruptedException, IOException {
        //创建信息
        Message message = new Message(topic, "我是一个异步的信息".getBytes());
        //异步消息就是在此调用了new SendCallback()来完成操作，重写发送成功和发送失败的逻辑。
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送成功:{}", sendResult.getSendStatus().toString());
            }
            @Override
            public void onException(Throwable e) {
                log.info("发送失败:{}", e.getMessage());
            }
        });

        System.in.read();
    }

    /**
     * 发送单向信息
     * 发送方并不关心消息是否被成功消费，消息一旦发送出去，就不再追踪其状态，这意味着如果消息在网络传输过程中丢失或者 Broker 出现故障，消息将无法被重发
     * 适用于不需要确认消息是否成功到达消费者的应用场景，例如：日志记录、监控数据上报
     *
     * @throws MQClientException
     * @throws MQBrokerException
     * @throws RemotingException
     * @throws InterruptedException
     */
    @Test
    public void onewaySend() throws MQClientException, RemotingException, InterruptedException, IOException {
        //创建信息
        Message message = new Message(topic, "我是一个单向的信息".getBytes());
        //异步消息就是在此调用了new SendCallback()来完成操作，重写发送成功和发送失败的逻辑。
        producer.sendOneway(message);
    }

    /**
     * 发送延迟消息
     * 设置消息在发送后经过一段时间延迟再被消费
     * 场景实例：下订单业务，提交了一个订单就可以发送一个延时消息,30min 后去检查这个订单的状态如果还是未付款就取消订单释放库存
     *
     * @throws Exception
     */
    @Test
    public void sendDelay() throws Exception {
        Message msg = new Message(topic, "延迟消息".getBytes());
        // 设置延迟等级3，即10s后才能被消费
        // 延迟等级默认一共18个等级，从1开始，依次为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        msg.setDelayTimeLevel(3);
        // 发送单向消息
        SendResult sendResult = producer.send(msg);
        log.info("sendResult:{}", sendResult.getSendStatus().toString());
    }

    /**
     * 顺序发送消息
     * 默认情况下，生产者把消息发送到队列用的是轮询的方式，而消费者在默认情况下接收消息也是轮询的方式，消费者默认情况下是用多线程的并发模式接收消息，这时候就不能保证接收消息的顺序
     * 如果要确保消息的顺序，可以将消息发送到同一个队列中
     */
    @Test
    public void sendByOrder() throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        //创建信息
        List<Message> msgs = Arrays.asList(
                new Message(topic, "消息1".getBytes()),
                new Message(topic, "消息2".getBytes()),
                new Message(topic, "消息3".getBytes())

        );

        // 自定义队列选择器，固定放到第一个队列
        MessageQueueSelector messageQueueSelector = new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                log.info("当前主题队列数:{}，arg:{}", mqs.size(), arg);
                // 返回选择的队列
                return mqs.get(0);
            }
        };

        for (Message msg : msgs) {
            producer.send(msg, messageQueueSelector, "额外参数");
        }
    }

}
