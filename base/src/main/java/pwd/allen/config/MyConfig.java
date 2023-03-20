package pwd.allen.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pwd.allen.entity.MyBean;
import pwd.allen.property.MyProperties;

/**
 * @author pwd
 * @create 2019-02-14 16:06
 **/
@ConditionalOnWebApplication
@Configuration
@EnableConfigurationProperties(MyProperties.class) //加入自定义配置类
public class MyConfig {

    @Bean(initMethod = "initMethod")
    public MyBean myBean() {
        return new MyBean();
    }
}
