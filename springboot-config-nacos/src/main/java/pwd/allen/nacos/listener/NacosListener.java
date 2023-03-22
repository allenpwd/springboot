package pwd.allen.nacos.listener;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.net.spi.nameservice.NameService;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * 自定义nacos监听器
 *
 * TODO 是否能指定nacos，而不是用全局的配置
 * @author 门那粒沙
 * @create 2021-09-04 17:55
 **/
@Slf4j
@Component
public class NacosListener {

    /**
     * 能通过@NacosInjected注解注入ConfigService
     *
     * 原理：{@link com.alibaba.nacos.spring.beans.factory.annotation.AnnotationNacosInjectedBeanPostProcessor}
     */
    @NacosInjected
    private ConfigService configService;

    @PostConstruct
    public void init() throws NacosException {
        // 向nacos上传配置
        boolean b = configService.publishConfig("my_config", "DEFAULT_GROUP", "pwd.config.local=代码里发布的属性", ConfigType.PROPERTIES.name());
        log.info("向nacos上传配置结果:{}", b);

        // 获取并监听配置
        String config = configService.getConfigAndSignListener("my_config", "DEFAULT_GROUP", 1000 * 10, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("监听到my_config变更：{}", configInfo);
            }
        });
        log.info("从nacos上读取配置：{}", config);
    }

    /**
     * 监听配置变更
     *
     * 等同于
     * configService.addListener(DATA_ID, DEFAULT_GROUP, new AbstractListener())
     * @param data
     */
    @NacosConfigListener(dataId = "json_config", groupId = "DEFAULT_GROUP", converter = DataConfigConverter.class)
    public void onMessage(Data data) {
        log.info("监听到json_config变更：{}", data);
    }
}
