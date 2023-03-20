package pwd.allen;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pwd.allen.controller.MyController;
import pwd.allen.property.MyProperties;

import java.io.File;
import java.util.Map;

/**
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@RunWith(SpringRunner.class) //SpringBoot1.4版本之前用的是SpringJUnit4ClassRunner.class
//SpringBoot1.4版本之前用的是@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
@AutoConfigureMockMvc // 添加后能自动注入MockMvc
@Slf4j
public class AppTest {

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        log.info("myProperties {}", myProperties);
    }

    /**
     * 1、mockMvc.perform执行一个请求。
     * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
     * 3、ResultActions.param添加请求传值
     * 4、ResultActions.accept(MediaType.TEXT_HTML_VALUE))设置返回类型
     * 5、ResultActions.andExpect添加执行完成后的断言。
     * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
     *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
     * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
     *
     * 遇到的坑
     * 1、地址前面没有/会404
     * 2、配置的server.servlet.context-path不需要拼上去
     */
    @Test
    public void mvc() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/my/myConfig").accept(MediaType.APPLICATION_JSON).param("name", "pwd"))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("abc"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * ConfigurableListableBeanFactory.getDependenciesForBean
     */
    public void dependencies() {
        MyController bean = applicationContext.getBean(MyController.class);
    }

}