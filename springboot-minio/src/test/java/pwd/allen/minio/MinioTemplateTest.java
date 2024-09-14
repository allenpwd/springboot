package pwd.allen.minio;

import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pwd.allen.minio.config.MinioConfig;
import pwd.allen.minio.service.IFileService;
import pwd.allen.minio.service.MinioTemplate;

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
    @Qualifier("minioTemplate")
//    @Qualifier("s3Tempalte")
    private IFileService fileService;

    String buckName =  "my-bucket";
    @Test
    void createBucket() {
        fileService.createBucket(buckName);
    }

    @Test
    void getObjectURL() {
        String objectURL = fileService.getObjectURL(buckName, "application.yml", 60);
        System.out.println(objectURL);
    }

    @Test
    void getObject() {
        InputStream inputStream = fileService.getObject(buckName, "application.yml");
        System.out.println(IoUtil.read(inputStream, Charset.defaultCharset()));
    }

    @Test
    void putObject() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.yml");
        fileService.putObject(buckName, "application.yml", inputStream);
        inputStream.close();
    }

    @Test
    void removeObject() {
        fileService.removeObject(buckName, "application.yml");
    }
}
