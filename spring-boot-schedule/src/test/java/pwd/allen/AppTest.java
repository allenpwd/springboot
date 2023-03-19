package pwd.allen;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.task.SimpleTask;

/**
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppTest {

    @Autowired
    private SimpleTask simpleTask;

    @Test
    public void async() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            simpleTask.printTask();
        }

        Thread.sleep(3000);

        for (int i = 0; i < 5; i++) {
            simpleTask.printTask();
        }
    }

}