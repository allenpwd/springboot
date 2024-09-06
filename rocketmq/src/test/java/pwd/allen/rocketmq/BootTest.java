package pwd.allen.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RocketMqMain.class)
public class BootTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void testSend() throws InterruptedException {

        template.convertAndSend("test-topic", "hello world");
        Thread.sleep(1000L);
    }
}
