package pwd.allen.minio.config;

import io.minio.MinioClient;
import io.minio.MinioProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 */
@Data
@Configuration
@EnableConfigurationProperties(MyMinioProperties.class)
public class MinioConfig {

    @Bean
    public MinioClient getMinioClient(MyMinioProperties properties)
    {
        return MinioClient.builder().endpoint(properties.getUrl()).credentials(properties.getAccessKey(), properties.getSecretKey()).build();
    }
}
