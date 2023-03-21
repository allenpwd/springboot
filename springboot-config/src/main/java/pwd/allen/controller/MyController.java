package pwd.allen.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.HelloService;
import pwd.allen.property.MyProperties;

import java.util.Map;

/**
 * @author pwd
 * @create 2019-02-14 16:03
 **/
@RequestMapping("my")
@RestController
@Api(tags = "我的控制器")
@Slf4j
public class MyController {

    //#{spEL} spEL里可以用${key}引用属性值，先解析占位符，再解析spel
    @Value("hello.prefix=${hello.prefix}\nifEnable=#{${pwd.my-config.bool}==true}")
    private String strValue;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private HelloService helloService;

    /**
     * produces：指定返回值类型，还可以设定返回值的字符编码
     * @param paramMap
     * @return
     */
    @ApiOperation(value = "我的配置", notes = "我的配置")
    @GetMapping(value = "config")
    public Object showMyConfig(@RequestParam Map paramMap) {

        if (paramMap.containsKey("sleep")) {
            Integer sleep = Integer.parseInt((String)paramMap.get("sleep"));
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info("paramMap {}", paramMap);

        paramMap.put("strValue", strValue);
        paramMap.put("myProperties", myProperties);
        paramMap.put("sayHello", helloService.getHelloStr("门那粒沙"));
        paramMap.put("empty", null);

        return paramMap;
    }

}
