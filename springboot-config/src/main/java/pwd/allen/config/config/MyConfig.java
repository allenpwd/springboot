package pwd.allen.config.config;

import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.context.annotation.Configuration;
import pwd.allen.config.property.MyProperties;

/**
 * 配置文件加载逻辑： TODO 看不懂 {@link org.springframework.boot.context.config.ConfigDataEnvironment#processAndApply()}
 * 它会读取spring.config.name和spring.config.location属性，以确定要使用的配置文件名和位置。
 * 同时，如果存在spring.config.additional-location属性，则还会查找位于该位置的配置文件
 *
 * 旧版是{@link org.springframework.boot.context.config.ConfigFileApplicationListener}
 *
 *
 * 新版是{@link org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor} 2.4之后引入的，如果出错会继续使用旧版的方式
 * 引入：通过spring.factories方式被获取，只在需要时实例化，并不会放入容器中
 * 时机：在ApplicationEnvironmentPreparedEvent事件期间被调用。
 *  -》{@link EnvironmentPostProcessorApplicationListener#onApplicationEnvironmentPreparedEvent(org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent)}
 *  -》{@link ConfigDataEnvironmentPostProcessor#postProcessEnvironment(org.springframework.core.env.ConfigurableEnvironment, org.springframework.boot.SpringApplication)}
 *
 * @author allen
 * @create 2023-03-21 19:54
 **/
@EnableConfigurationProperties(MyProperties.class)
@Configuration
public class MyConfig {
}
