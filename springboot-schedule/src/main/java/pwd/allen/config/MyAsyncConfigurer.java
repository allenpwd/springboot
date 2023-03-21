package pwd.allen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * 自定义异步处理器为自己的那个ttlExecutor
 * @author allen
 * @create 2023-03-19 17:55
 **/
public class MyAsyncConfigurer extends AsyncConfigurerSupport {

    /**
     * 指定注入beanName为ttlExecutor的那个
     */
    @Qualifier("ttlExecutor")
    @Autowired
    private Executor executor;

    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }
}
