package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum implements IEnum<Integer> {
    ON(1, "启用"),
    OFF(0, "禁用");

    private int value;
    @JsonValue
    private String desc;

    StatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "StatusEnum{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                '}';
    }
}
