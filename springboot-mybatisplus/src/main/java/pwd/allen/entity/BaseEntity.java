package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    /**
     * 如果子类重新定义了这个字段，TableField是否会被继承
     * 结果：不会被继承，所以子类需要重新标注注解
     */
    @TableField(exist = false)
    private String notExist;
}
