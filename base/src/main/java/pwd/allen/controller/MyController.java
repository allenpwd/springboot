package pwd.allen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.hellospringbootstarterautoconfigurer.HelloService;
import pwd.allen.property.MyProperties;

import java.util.Map;

/**
 * @author pwd
 * @create 2019-02-14 16:03
 **/
//@ConditionalOnExpression("${controller.MyController.enabled}==true")    //开启的条件是：controller.MyController.enabled=true
//@ConditionalOnProperty(prefix = "controller.MyController", value = "enabled", matchIfMissing = true)    //默认也会开启
@RequestMapping("my")
@RestController
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    //#{spEL} spEL里可以用${key}引用属性值
    @Value("strValue=${controller.MyController.strValue}\nifEnable=#{${controller.MyController.enabled}==true}")
    private String strValue;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "myConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showMyConfig(@RequestParam Map paramMap) {

        logger.info("paramMap {}", paramMap);

        paramMap.put("myProperties", myProperties);
        paramMap.put("sayHello", helloService.getHelloStr("门那粒沙"));

        return paramMap;
    }

}
