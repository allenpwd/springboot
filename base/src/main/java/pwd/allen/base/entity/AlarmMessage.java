package pwd.allen.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 门那粒沙
 * @create 2021-03-07 17:54
 **/
@Data
public class AlarmMessage {

    @ApiModelProperty("类型ID")
    private int scopeId;

    @ApiModelProperty("类型名称")
    private String scope;

    @ApiModelProperty("实体名称")
    private String name;
    private String id0;

    @ApiModelProperty("告警规则名称")
    private String ruleName;
    private String id1;

    @ApiModelProperty("告警消息")
    private String alarmMessage;

    @ApiModelProperty("告警产生时间")
    private long startTime;
}
