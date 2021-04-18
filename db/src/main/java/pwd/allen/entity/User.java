package pwd.allen.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @author 门那粒沙
 * @create 2020-06-16 23:27
 **/
@TableName("db_user")
@Data
public class User extends Model<User> {

    /**
     * 标注主键
     *  id:字段名
     *  type:主键类型
     *      ASSIGN_ID：分配ID(主键类型为Number(Long和Integer)或String)(since 3.3.0),使用接口IdentifierGenerator的方法nextId
     *      AUTO：数据库ID自增，好像即使手动设置id也不会传
     */
    @TableId(value = "id")
    private Integer id;
    private String userName;
    @TableField(updateStrategy = FieldStrategy.IGNORED) //为空时也更新到数据库
    private Integer age;
    private Integer deptId;
    private Date createAt;
}
