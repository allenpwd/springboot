package pwd.allen.base.entity;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @JsonValue 注解指定序列化时要返回的属性值
 * @author lenovo
 * @create 2020-06-17 11:13
 **/
public enum StatusEnum {

    ENABLED(0, "启用"), DISABLED(1, "禁用");

//    @JsonValue
    private final Integer code;
    private final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
