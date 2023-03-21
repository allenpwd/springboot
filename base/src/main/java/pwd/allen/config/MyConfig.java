package pwd.allen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pwd.allen.entity.MyBean;

/**
 * @author pwd
 * @create 2019-02-14 16:06
 **/
@Configuration
public class MyConfig {

    @Bean(initMethod = "initMethod")
    public MyBean myBean() {
        return new MyBean();
    }
}
