package pwd.allen.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pwdan
 * @create 2023-10-08 17:56
 **/
@Data
@ConfigurationProperties("minio")
public class MyMinioProperties {
    /**
     * 服务地址
     */
    private String url;

    /**
     * 用户名
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;
}
