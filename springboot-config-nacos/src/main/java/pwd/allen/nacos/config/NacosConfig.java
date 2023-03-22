package pwd.allen.nacos.config;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import org.springframework.boot.SpringApplication;

/**
 * 配置引入原理：
 *  引入了NacosConfigApplicationContextInitializer，会在Environment准备好后，开始准备上下文的时候被回调
 *  -》{@link SpringApplication#applyInitializers(org.springframework.context.ConfigurableApplicationContext)}
 *  -》{@link com.alibaba.boot.nacos.config.autoconfigure.NacosConfigApplicationContextInitializer#initialize}
 *
 *
 * 动态刷新原理：
 *  引入了NacosValueAnnotationBeanPostProcessor，会监听配置改变事件，当配置改变时，nacos-client会调用spring的事件发布器发布事件，并带上配置信息
 *      -》{@link com.alibaba.nacos.spring.context.annotation.config.NacosValueAnnotationBeanPostProcessor#onApplicationEvent}
 * @author allen
 * @create 2023-03-22 21:46
 **/
@EnableNacosConfig
//@NacosPropertySources({@NacosPropertySource(dataId = "ymal_config", groupId = "DEFAULT_GROUP", autoRefreshed = true)})
public class NacosConfig {
}
