package pwd.allen.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pwdan
 * @create 2023-09-18 9:09
 **/
@ApiModel(description = "我的实例")
@Data
public class MyEntity {
    private String strA;
    private Integer intA;
    private Float floatA;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createAt;
    private Map<String, Object> mapA;
    private List<String> listA;
}
