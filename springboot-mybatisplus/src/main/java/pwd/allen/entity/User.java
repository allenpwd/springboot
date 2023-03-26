package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * TODO
 * @Version:乐观锁注解
 * @EnumValue:枚举
 *
 * @author 门那粒沙
 * @create 2020-06-16 23:27
 **/
@TableName("db_user")
@Data
@ApiModel(value = "用户实体", description = "用户实体")
public class User extends Model<User> {

    /**
     * 标注主键
     *  id:字段名
     *  type:主键类型
     *      ASSIGN_ID：分配ID(主键类型为Number(Long和Integer)或String)(since 3.3.0),使用接口IdentifierGenerator的方法nextId，默认雪花算法 {@link com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator}
     *      AUTO：数据库ID自增，好像即使手动设置id也不会传
     *
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "knife4j")
    private String userName;
    @TableField(updateStrategy = FieldStrategy.IGNORED) //为空时也更新到数据库
    @OrderBy(asc = true, sort = 1) // 指定排序，优先级低于wrapper条件查询
    private Integer age;
    private StatusEnum status;
    private Integer deptId;
    @TableField(fill = FieldFill.INSERT_UPDATE) // //插入或者更新都进行填充处理，需借助MetaObjectHandler，判断优先级比 {@link FieldStrategy} 高
    private Date createAt;
    private String msg;
    private BigDecimal deposit;

    /**
     * 数据库中不存在的字段要显性排除掉
     * 另一种排除方法是：{@link TableName(exCludeProperty={"notExist"})}
     */
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private String notExist;
}
