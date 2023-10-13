package pwd.allen.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 自定义配置类，映射pwd.myConfig下的配置
 *
 * 使用步骤：
 *  属性类增加注解：{@link ConfigurationProperties}，指定配置前缀
 *  引入属性类，有两种方式
 *      方式一：在配置类加上{@link org.springframework.boot.context.properties.EnableConfigurationProperties} 并value属性指定属性类
 *      方式二：直接加入spring容器，会自动被后置处理器处理
 *
 * 原理：
 *  自动装配类ConfigurationPropertiesAutoConfiguration引入了EnableConfigurationPropertiesRegistrar
 * @see org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar
 *
 * 问题：
 *  （1）Canonical names should be kebab-case ('-' separated), lowercase alpha-numeric characters and must start with a letter
 *  原因：prefix属性不能用驼峰命名，可以用-分隔符
 *
 * @author pwd
 * @create 2019-02-14 16:04
 **/
@ConfigurationProperties(prefix = "pwd.my-config")
//@Component
@Data
//@Validated
public class MyProperties {


//                                                  @ConfigurationProperties	 @Value
//    功能	                                        批量注入配置文件中的属性	     一个个指定
//    松散绑定（例如驼峰、-或者_分隔、不区分大小写）	支持	                     不支持
//    SpEL	                                        不支持	                     支持
//    JSR303数据校验	                                支持	                     不支持（新版本支持）
//    复杂类型封装	                                支持                         不支持

    @Email
    @Value("os.name=${os.name}")//ConfigurationProperties的赋值会覆盖@Value的
    private String myStr;
    private Integer integer;
    private Boolean bool;
    private Date date;
    private Map map1;
    private Map map2;
    private Map map3;
    private Map map4;
    private List list1;
    private List list2;
    private List list3;
    /**
     * 不知道这个注解有何用 不加上也能正常嵌套赋值
     */
//    @NestedConfigurationProperty
    private ErrorProperties errorProperties;
}


