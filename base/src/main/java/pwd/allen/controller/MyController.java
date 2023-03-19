package pwd.allen.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pwd.allen.HelloService;
import pwd.allen.entity.AlarmMessage;
import pwd.allen.property.MyProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
@Api(tags = "我的控制器")
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    //#{spEL} spEL里可以用${key}引用属性值
    @Value("strValue=${controller.MyController.strValue}\nifEnable=#{${controller.MyController.enabled}==true}\npwd.my-config.integer=${pwd.my-config.integer}")
    private String strValue;

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private HelloService helloService;

    @Autowired
    private WebApplicationContext applicationContext;

    /**
     * produces：指定返回值类型，还可以设定返回值的字符编码
     * @param paramMap
     * @return
     */
    @ApiOperation(value = "我的配置", notes = "我的配置")
    @GetMapping(value = "myConfig", produces = MediaType.APPLICATION_JSON_VALUE)
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
        paramMap.put("empty", null);

        return paramMap;
    }

    /**
     * 转发
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("forward")
    public Object forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/my/myConfig").forward(req, resp);
        return "forward test";
    }

    @GetMapping("log")
    public Object log() {
        logger.info("info");
        logger.warn("warn");
        logger.debug("debug");
        return new AlarmMessage();
    }

    @Value("${uploadPath:/opt/IBM/ABC/test/file/}")
    private String uploadPath;

    @PostMapping("upload")
    public Object upload(MultipartHttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", file.getSize());
        map.put("originalFilename", file.getOriginalFilename());
        map.put("type", "success");
        String str_date = DateUtil.format(new Date(), "yyyyMMdd");
        File saveFile = new File(String.format("%s/%s/%s", uploadPath, str_date, file.getOriginalFilename()));
        FileUtil.mkParentDirs(saveFile);
        // 用这个报错说找不到路径
//        file.transferTo(saveFile);
        try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
        }
        map.put("path", saveFile.getAbsolutePath());
        return map;
    }

}
