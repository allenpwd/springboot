package pwd.allen.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author pwdan
 * @create 2023-09-13 9:29
 **/
@Data
@AllArgsConstructor
public class MyResult<T> {

    @ApiModelProperty(value = "结果码", example = "200")
    private Integer code;
    @ApiModelProperty(value = "结果描述", example = "success")
    private String msg;
    private T data;

    public static <T> MyResult<T> success(T data) {
        return new MyResult<>(200, "success", data);
    }

    public static <T> MyResult<T> error(T data) {
        return new MyResult<>(500, "error", data);
    }
}
