package pwd.allen.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义配置
 *
 * @author pwd
 * @create 2019-02-14 16:04
 **/

/**
 * 将配置文件中指定前缀的属性封装到该实例；不能用驼峰命名，可以用-分隔符
 */
@ConfigurationProperties("pwd.my-config")
@Data
public class MyProperties {

    private String str;
    private Integer integer;

}


