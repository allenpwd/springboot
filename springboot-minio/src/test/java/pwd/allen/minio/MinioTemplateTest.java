package pwd.allen.minio;

import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pwd.allen.minio.config.MinioConfig;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author pwdan
 * @create 2023-10-11 17:35
 **/
@Import(MinioTemplate.class)
@SpringBootTest(classes = MinioConfig.class)
class MinioTemplateTest {
    @Autowired
    private MinioTemplate minioTemplate;

    String buckName =  "my-bucket";
    @Test
    void createBucket() {
        minioTemplate.createBucket(buckName);
    }

    @Test
    void getObjectURL() {
        String objectURL = minioTemplate.getObjectURL(buckName, "application.yml", 60);
        System.out.println(objectURL);
    }

    @Test
    void getObject() {
        InputStream inputStream = minioTemplate.getObject(buckName, "application.yml");
        System.out.println(IoUtil.read(inputStream, Charset.defaultCharset()));
    }

    @Test
    void putObject() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.yml");
        minioTemplate.putObject(buckName, "application.yml", inputStream);
        inputStream.close();
    }

    @Test
    void removeObject() {
        minioTemplate.removeObject(buckName, "application.yml");
    }
}