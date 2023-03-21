package pwd.allen.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pwd.allen.property.MyProperties;

/**
 * 配置文件加载逻辑：
 *   旧版是{@link org.springframework.boot.context.config.ConfigFileApplicationListener}
 *   新版是{@link org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor}
 *
 * @author allen
 * @create 2023-03-21 19:54
 **/
@EnableConfigurationProperties(MyProperties.class)
@Configuration
public class MyConfig {
}
