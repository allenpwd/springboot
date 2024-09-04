package pwd.allen.rocketmq.constant;

/**
 */
public enum MQConstant {

    TOPIC_DQ("topic_data_queue_message"),
    TOPIC_USER("sync-user-info"),
    TOPIC_MESSAGE("topic_message");

    private final String constant;

    MQConstant(String constant){
        this.constant = constant;
    }
    public String getConstant(){
        return constant;
    }
}
