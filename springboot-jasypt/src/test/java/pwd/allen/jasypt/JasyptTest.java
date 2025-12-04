package pwd.allen.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author pwdan
 * @create 2025-12-04 16:50
 **/
public class JasyptTest {

    private final static String SECRECT = "jasypt"; //秘钥
    private final static String ALGORITHM = "PBEWithMD5AndDES"; //加密算法

    private StandardPBEStringEncryptor standardPBEStringEncryptor;

    @BeforeEach
    public void init() {
        standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm(ALGORITHM);
        config.setPassword(SECRECT);
        standardPBEStringEncryptor.setConfig(config);
    }

    @Test
    public void
    testEncrypt() throws Exception {
        String text = "/jasypt";
        String encrypt = standardPBEStringEncryptor.encrypt(text);
        System.out.println("密文密码：" + encrypt);
        System.out.println("明文密码：" + standardPBEStringEncryptor.decrypt(encrypt));
    }

}
