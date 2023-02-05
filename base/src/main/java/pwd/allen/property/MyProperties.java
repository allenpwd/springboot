package pwd.allen.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import pwd.allen.entity.AlarmMessage;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 自定义配置
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 * prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 使用步骤：
 *  配置类增加注解：@EnableConfigurationProperties
 *  属性类增加注解：@ConfigurationProperties，指定配置前缀
 *  属性类可以在@EnableConfigurationProperties的属性中指定，也可以直接加入spring容器，会自动被后置处理器处理
 *
 * 原理：
 *  自动装配类ConfigurationPropertiesAutoConfiguration引入了EnableConfigurationPropertiesRegistrar
 * @see org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar
 *
 * @author pwd
 * @create 2019-02-14 16:04
 **/
//prefix属性不能用驼峰命名，可以用-分隔符，否则会报错：
// Canonical names should be kebab-case ('-' separated), lowercase alpha-numeric characters and must start with a letter
@ConfigurationProperties("pwd.my-config")
@Data
//@Component
//@Validated
public class MyProperties {


//                                                  @ConfigurationProperties	 @Value
//    功能	                                        批量注入配置文件中的属性	     一个个指定
//    松散绑定（例如驼峰、-或者_分隔、不区分大小写）	支持	                         不支持
//    SpEL	                                        不支持	                     支持
//    JSR303数据校验	                                支持	                         不支持（新版本支持）
//    复杂类型封装	                                支持                         不支持

    @Email
    @Value("os.name=${os.name}")//ConfigurationProperties的赋值会覆盖@Value的
    private String myStr;
    private Integer integer;
    private Boolean bool;
    private Date date;
    private Map map1;
    private Map map2;
    private List list1;
    private List list2;
    private List<AlarmMessage> list3;

    /**
     * 不知道这个注解有何用 不加上也能正常嵌套赋值
     */
    @NestedConfigurationProperty
    private ErrorProperties errorProperties = new ErrorProperties();
}


