package pwd.allen.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @NotEmpty
//    @org.hibernate.validator.constraints.Length(max = 10, message = "strA字符串长度最大不能超过10")
    private String strA;
    private Integer intA;
    private Float floatA;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createAt;
    private Map<String, Object> mapA;
    @Size(max = 10, message = "list长度最大不能超过10")
    private List<String> listA;
}
