package pwd.allen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

/**
 * 启动嵌入式web服务器
 * @SpringBootTest
 *  WebEnvironment取值：
 *      MOCK（默认）：加载 web ApplicationContext 并提供模拟web环境。使用此注解时，嵌入式服务器未启动。如果类路径上没有可用的web环境，则此模式会透明地回退到创建常规的非web ApplicationContext。它可以与 @AutoConfigureMockMvc 或 @AutoConfigureWebTestClient 结合使用，对web应用程序进行基于模拟的测试。
 *      RANDOM_PORT：加载 WebServerApplicationContext 并提供真正的web环境。嵌入式服务器启动并在随机端口上监听。
 *      DEFINED_PORT：加载 WebServerApplicationContext 并提供真正的web环境。嵌入式服务器将启动并在定义的端口（从 application.properties）或默认端口8080上监听。
 *      NONE：使用 SpringApplication 加载 ApplicationContext，但不提供任何web环境（mock或其他）。
 *
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // 添加后能自动注入MockMvc
public class WithServerTest {

    private static final Logger logger = LoggerFactory.getLogger(WithServerTest.class);

    /**
     * 获取服务器端口
     */
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    /**
     * 需要指定启动服务器的方式才能使用
     * TestRestTemplate 比 RestTemplate 在测试环境里多做了很多事,
     *     // 比如: 帮你自己把当前 host:port 加上了. (尤其是咱们还指定了随机端口)
     *     //      能自动加账号密码,
     *     //      ErrorHandler 被设成了 NoOpResponseErrorHandler.
     */
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void template() {
        System.out.println("端口为：" + port);
        ResponseEntity<Map> response = restTemplate.getForEntity("/my/myConfig", Map.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

}