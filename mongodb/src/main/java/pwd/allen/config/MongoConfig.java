package pwd.allen.config;

import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Mongodb配置
 * TODO 升级后不兼容了
 * @author lenovo
 * @create 2021-03-25 10:11
 **/
@Configuration
public class MongoConfig implements MongoClientSettingsBuilderCustomizer {

    @Value("${mongo.timeout:1000}")
    private int timeout;

    @Override
    public void customize(MongoClientSettings.Builder clientSettingsBuilder) {

    }

//    /**
//     * 旧版是 {@link com.mongodb.MongoClientOptions}
//     * 新版是否变成 {@link MongoClientSettings}
//     * @return
//     */
//    @Bean
//    public MongoClientSettingsBuilderCustomizer mongoClientOptions() {
//        return MongoClientSettings.builder()
//                //连接超时
//                .
//                .connectTimeout(timeout)
//                //选择一个可达服务的超时时间
//                .serverSelectionTimeout(timeout)
//                .socketTimeout(timeout)
//                .build();
//    }
}
