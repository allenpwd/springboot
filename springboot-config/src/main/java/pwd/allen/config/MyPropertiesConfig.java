package pwd.allen.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pwd.allen.property.MyProperties;

/**
 * @author pwdan
 * @create 2023-03-21 15:59
 **/
@EnableConfigurationProperties(MyProperties.class) //加入自定义配置类
@Configuration
public class MyPropertiesConfig {
}
