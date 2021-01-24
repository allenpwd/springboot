package pwd.allen.controller;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import pwd.allen.HelloService;
import pwd.allen.property.MyProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private WebApplicationContext applicationContext;

    private static final ThreadLocal<Object> THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * produces：指定返回值类型，还可以设定返回值的字符编码
     * @param paramMap
     * @return
     */
    @RequestMapping(value = "myConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showMyConfig(@RequestParam Map paramMap) {

        if (paramMap.containsKey("sleep")) {
            Integer sleep = Integer.parseInt((String)paramMap.get("sleep"));
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("paramMap {}", paramMap);

        paramMap.put("strValue", strValue);
        paramMap.put("myProperties", myProperties);
        paramMap.put("sayHello", helloService.getHelloStr("门那粒沙"));

        return paramMap;
    }

    @GetMapping("async")
    public Object async(HttpServletRequest request) throws InterruptedException {
        THREAD_LOCAL.set(String.format("这里是父线程:%s", Thread.currentThread().getName()));

        // 异步调用
        applicationContext.getBean(MyController.class).execAsync();
        return "hello";
    }

    @Async
    public void execAsync() throws InterruptedException {
        logger.info("【{}】从父线程获取的变量：{}", Thread.currentThread().getName(), THREAD_LOCAL.get());
        TimeUnit.SECONDS.sleep(5);
    }

}
