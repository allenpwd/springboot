package pwd.allen.config;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Mongodb配置
 *
 * @author lenovo
 * @create 2021-03-25 10:11
 **/
@Configuration
public class MongoConfig {

    @Value("${mongo.timeout:1000}")
    private int timeout;

    @Bean
    public MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder()
                //连接超时
                .connectTimeout(timeout)
                //选择一个可达服务的超时时间
                .serverSelectionTimeout(timeout)
                .socketTimeout(timeout)
                .build();
    }
}
