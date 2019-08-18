package pwd.allen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.property.MyProperties;

/**
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest01 {

    @Autowired
    private MyProperties myProperties;

    @Test
    public void test() {
        System.out.println("------------------------------------" + myProperties);
    }

}