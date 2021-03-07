package pwd.allen.entity;

import lombok.Data;

/**
 * @author 门那粒沙
 * @create 2021-03-07 17:54
 **/
@Data
public class AlarmMessage {

    private int scopeId;
    private String name;
    private int id0;
    private int id1;
    /**
     * 告警消息
     */
    private String alarmMessage;
    /**
     * 告警产生时间
     */
    private long startTime;
}
