package pwd.allen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pwd.allen.controller.MyController;
import pwd.allen.property.MyProperties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 门那粒沙
 * @create 2021-05-07 21:11
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(MyController.class)
@EnableConfigurationProperties(MyProperties.class)//加入自定义配置类
public class MyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private HelloService helloService;

    @Test
    public void showConfig() throws Exception {
        String str = "耶稣都留不住，我说的！";
        Mockito.when(helloService.getHelloStr(ArgumentMatchers.anyString())).thenReturn(str);
        System.out.println(helloService.getHelloStr("abc"));



        mvc.perform(post("/my/myConfig").param("name", "pwd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sayHello").value(str))
                .andDo(MockMvcResultHandlers.print());
    }
}
