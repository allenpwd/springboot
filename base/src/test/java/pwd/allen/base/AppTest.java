package pwd.allen.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pwd.allen.base.controller.MyController;

/**
 * 新版本只需要加上@SpringBootTest而不需要@RunWith
 * @author 门那粒沙
 * @create 2019-08-18 22:46
 **/
@SpringBootTest
@AutoConfigureMockMvc // 添加后能自动注入MockMvc
@Slf4j
public class AppTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApplicationContext applicationContext;

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
        mvc.perform(MockMvcRequestBuilders.get("/my/get/abc").param("num", "1234"))
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