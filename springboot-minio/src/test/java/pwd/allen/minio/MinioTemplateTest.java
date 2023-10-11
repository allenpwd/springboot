package pwd.allen.minio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import pwd.allen.minio.config.MinioConfig;

/**
 * @author pwdan
 * @create 2023-10-11 17:35
 **/
@Import(MinioTemplate.class)
@SpringBootTest(classes = MinioConfig.class)
class MinioTemplateTest {

    @Autowired
    private MinioTemplate minioTemplate;


    @Test
    void createBucket() {
        System.out.println(minioTemplate);
    }

    @Test
    void getObjectURL() {
    }

    @Test
    void getObject() {
    }

    @Test
    void putObject() {
    }

    @Test
    void testPutObject() {
    }

    @Test
    void removeObject() {
    }
}