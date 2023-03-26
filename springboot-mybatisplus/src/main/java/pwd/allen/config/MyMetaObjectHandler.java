package pwd.allen.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 自定义公共字段填充处理器
 * 使用方式
 *  加入spring容器中即可被mybatis使用，原理：TODO
 *  要处理的字段指定填充策略：@TableField(fill = FieldFill.INSERT_UPDATE)
 *
 * @author 门那粒沙
 * @create 2020-06-25 23:25
 **/
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("createAt", metaObject);
        if (fieldValue == null) {
            // 默认当前时间
            strictInsertFill(metaObject, "createAt", Date.class, new Date());
        }
    }
    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("msg", metaObject);
        if (fieldValue == null) {
            // 如果msg字段为空，则填充默认的值
            strictInsertFill(metaObject, "msg", byte[].class, "默认填充值".getBytes());
        }
    }
}
