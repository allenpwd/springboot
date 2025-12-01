package pwd.allen.minio;

import cn.hutool.core.io.IoUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import pwd.allen.minio.service.IFileService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author pwdan
 * @create 2023-10-11 17:35
 **/
@SpringBootTest(classes = MinioMain.class)
class MinioTemplateTest {

    @Autowired
    @Qualifier("minioFileService")
//    @Qualifier("s3Template")
    private IFileService fileService;

    String buckName =  "my-bucket";
    @Test
    void createBucket() {
        fileService.createBucket(buckName);
    }

    /**
     * 预览地址的ip一旦生成不能使用其他ip进行访问，否则会验证失败，因为ip被用于签名验证，
     */
    @Test
    void getObjectURL() {
        String objectURL = fileService.getObjectURL(buckName, "test/秒杀+分布式锁.doc", 60);
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
