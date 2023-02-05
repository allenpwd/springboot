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
 * 原理：
 *  引入了AutoConfigurationImportSelector，beanFactory初始化后执行BeanFactory后置处理器的时候
 *  会由ConfigurationClassPostProcessor加载AutoConfigurationImportSelector的selectImports方法返回的配置类集合
 *  AutoConfigurationImportSelector是在getCandidateConfigurations方法会从META-INF/spring.factories中获取各个组件的自动配置类的全限定名，然后根据条件筛选出有效的配置类并返回
 *  AutoConfigurationImportSelector实现了DeferredImportSelector接口以及Ordered接口（优先级最低），所以能够在所有的@Configuration配置类（不包括自动化配置类，即spring.factories文件中的配置类）处理完成后最先运行
 *
 * @see org.springframework.boot.autoconfigure.AutoConfigurationImportSelector
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
