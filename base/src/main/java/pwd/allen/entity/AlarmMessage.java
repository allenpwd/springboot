package pwd.allen.entity;

import lombok.Data;

/**
 * @author 门那粒沙
 * @create 2021-03-07 17:54
 **/
@Data
public class AlarmMessage {

    private int scopeId;
    /**
     * 类型名称
     */
    private String scope;
    /**
     * 实体名称
     */
    private String name;
    private String id0;
    /**
     * 告警规则名称
     */
    private String ruleName;
    private String id1;
    /**
     * 告警消息
     */
    private String alarmMessage;
    /**
     * 告警产生时间
     */
    private long startTime;
}
