package pwd.allen.rocketmq.service;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwd.allen.rocketmq.constant.MQTags;
import pwd.allen.rocketmq.constant.MQTopic;
import pwd.allen.rocketmq.producer.SyncProducer;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:10
 */
@Slf4j
@Service
public class LocalService {

    @Autowired
    private SyncProducer syncProducer;

    public String executeLocalService(){
        String msg = "执行本地业务";
        return this.executeLocalService(msg);
    }

    public String executeLocalService(String msg){
        log.info("【执行业务，读取发送消息】：{}", msg);
        return msg;
    }

    public String sentSyncMessage(String s){
        TransactionSendResult result = syncProducer.sendSyncMessage(s, MQTopic.SPRINGBOOT_TOPIC.getTopic(), MQTags.SPRINGBOOT_TAG.getTag());
        return result.getLocalTransactionState().toString();
    }


}
