package pwd.allen.rocketmq.nativedemo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author pwdan
 * @create 2024-09-10 10:34
 **/
@Slf4j
public class transactionProductTest {

    private String namesrvAddr = "192.168.118.102:9876";
    private String producerGroup = "test-producer-group";
    private String topic = "test-topic";

    private TransactionMQProducer producer;

    @BeforeEach
    public void before() throws MQClientException {
        //创建一个生产者，制定一个组名
        producer = new TransactionMQProducer(producerGroup);
        //连接namesrv
        producer.setNamesrvAddr(namesrvAddr);
        // 设置事务消息监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 这个是执行本地业务方法
             * @param msg Half(prepare) message
             * @param arg Custom business parameter
             * @return LocalTransactionState
             *   COMMIT_MESSAGE 表示允许消费者消费该消息
             *   ROLLBACK_MESSAGE 表示该消息将被删除，不允许消费
             *   UNKNOW表示需要MQ回查才能确定状态，即后通过checkLocalTransaction(msg)方法进行回查
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                log.info("【executeLocalTransaction-{}】msg：{}", new Date(), new String(msg.getBody()));
                // 业务代码在这里处理
                // 如果成功消费，则返回COMMIT_MESSAGE
                // 如果失败则返回ROLLBACK_MESSAGE
                // 如果需要暂时无法确定，需要定时回查则返回UNKNOW
                return LocalTransactionState.UNKNOW;
            }


            /**
             * 这里是回查方法 回查不是再次执行业务操作，而是确认上面的操作是否有结果
             * 默认是1min回查 默认回查15次 超过次数则丢弃打印日志 可以通过参数设置
             * transactionTimeOut 超时时间
             * transactionCheckMax 最大回查次数
             * transactionCheckInterval 回查间隔时间单位毫秒
             * 触发条件
             *   1.当上面执行本地事务返回结果UNKNOW时,或者下面的回查方法也返回UNKNOW时 会触发回查
             *   2.当上面操作超过20s没有做出一个结果，也就是超时或者卡主了，也会进行回查
             * @param msg Check message
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                log.info("【checkLocalTransaction-{}】msg：{}", new Date(), new String(msg.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        //启动
        producer.start();
    }

    @AfterEach
    public void after() {
        //关闭生产者
        producer.shutdown();
    }

    /**
     * 发送事务消息
     *
     * @throws MQClientException
     */
    @Test
    public void syncSend() throws MQClientException, InterruptedException {
        //创建信息
        Message message = new Message(topic, "测试下事务信息".getBytes());
        //发送信息
        SendResult sendResult = producer.sendMessageInTransaction(message, null);
        log.info("sendResult:{}", sendResult.getSendStatus().toString());
        Thread.sleep(1000 * 2 * 60);
    }
}
