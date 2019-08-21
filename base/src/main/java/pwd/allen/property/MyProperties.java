package pwd.allen.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 自定义配置
 * 将配置文件中配置的每一个属性的值，映射到这个组件中
 * @ConfigurationProperties：告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定；
 * prefix = "person"：配置文件中哪个下面的所有属性进行一一映射
 *
 * 只有这个组件是容器中的组件，@ConfigurationProperties才有作用
 *
 * @author pwd
 * @create 2019-02-14 16:04
 **/
//prefix属性不能用驼峰命名，可以用-分隔符，否则会报错：
// Canonical names should be kebab-case ('-' separated), lowercase alpha-numeric characters and must start with a letter
@ConfigurationProperties("pwd.my-config")
@Data
public class MyProperties {

    private String myStr;
    private Integer integer;
    private Boolean bool;
    private Date date;
    private Map map1;
    private Map map2;
    private List list1;
    private List list2;
}


