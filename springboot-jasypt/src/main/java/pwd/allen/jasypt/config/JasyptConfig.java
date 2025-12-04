package pwd.allen.jasypt.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 配置加密密钥，务必保密
        config.setPassword("PIEYWBHAEEJAID");
        // 设置加密算法
        config.setAlgorithm("PBEWithMD5AndDES");
        // 设置密钥迭代次数，影响破解难度
        config.setKeyObtentionIterations("1000");
        // 设置加密池的大小，1 表示单个实例使用
        config.setPoolSize("1");
        // 盐生成器类，防止彩虹表攻击
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        // 设置输出编码类型为 base64
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}
