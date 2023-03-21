package pwd.allen.base.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pwd.allen.HelloService;
import pwd.allen.base.config.MyConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 只测试controller层
 * @WebMvcTest注解
 *  @WebMvcTest 自动配置Spring MVC基础结构，并将扫描的bean限制为@Controller、@ControllerAdvice、@JsonComponent、Converter、GenericConverter、Filter、HandlerInterceptor、WebMVCConfiguer和HandlerMethodArgumentResolver。
 *  使用此注解时，不扫描常规@Component bean。
 *  测试发现WebConfig2会被加载，怀疑是会解析WebMvcConfigurer类型
 *  如果不指定controller，则默认扫描所有Controller
 *
 * @author 门那粒沙
 * @create 2021-05-07 21:11
 **/
@WebMvcTest(MyController.class)
@ImportAutoConfiguration(MyConfig.class) //由于不会加载MyConfig，所以需要手动加入自定义配置类
public class MyControllerTest {

    /**
     * @WebMvcTest引入了@AutoConfigureMockMvc，所以可以使用MockMvc
     */
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HelloService helloService;

    @BeforeEach
    public void before() {
        RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) mvc.getDispatcherServlet().getHandlerMappings().get(0);
    }

    @Test
    public void showConfig() throws Exception {
        String str = "耶稣都留不住， 我说的！";
        Mockito.when(helloService.getHelloStr(ArgumentMatchers.anyString())).thenReturn(str);

        mvc.perform(get("/my/myConfig").param("name", "pwd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sayHello").value(str))
                .andDo(MockMvcResultHandlers.print());
    }
}
