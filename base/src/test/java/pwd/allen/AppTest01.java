package pwd.allen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.property.MyProperties;

import java.io.File;

/**
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest01 {

    private static final Logger logger = LoggerFactory.getLogger(AppTest01.class);

    @Autowired
    private MyProperties myProperties;

    @Test
    public void test() {
        logger.info("myProperties {}", myProperties);
    }

    @Test
    public void test2() {
        File file = new File("C:\\Users\\lenovo\\Desktop\\git\\.");
        System.out.println(file.getAbsolutePath());
    }

}