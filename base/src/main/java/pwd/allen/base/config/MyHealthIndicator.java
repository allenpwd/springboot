package pwd.allen.base.config;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 自定义 actuator 的 健康端点
 * health的名称是my，逻辑在 {@link org.springframework.boot.actuate.health.HealthIndicatorNameFactory}
 *
 * @author lenovo
 * @create 2019-12-11 12:42
 **/
@Component("myHealthIndicator")
public class MyHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.outOfService().withDetail("version", "1.0.0").withDetail("msg", "状态不好，撤退！");
    }
}
