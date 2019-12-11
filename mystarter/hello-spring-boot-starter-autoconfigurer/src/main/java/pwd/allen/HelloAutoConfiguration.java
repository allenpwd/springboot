package pwd.allen;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义启动场景
 * 一般专门写一个自动配置模块，然后启动器依赖自动配置，使用时只需引入启动器
 * 将需要启动加载的启动配置类配置在META-INF/spring.factories
 * org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
 * pwd.allen.hellospringbootstarterautoconfigurer.HelloAutoConfiguration
 *
 * @author lenovo
 * @create 2019-09-02 14:57
 **/
@Configuration
@EnableConfigurationProperties(HelloProperties.class)
@ConditionalOnWebApplication
public class HelloAutoConfiguration {

    @Bean
    public HelloService helloService(HelloProperties helloProperties) {
        HelloService helloService = new HelloService();
        helloService.setHelloProperties(helloProperties);
        return helloService;
    }
}
