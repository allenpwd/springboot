package pwd.allen.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author pwdan
 * @create 2019-10-06 9:35
 **/
@RestController
@RequestMapping("nacos")
public class NacosController {

    @Autowired
    private ConfigurableEnvironment environment;

    @NacosValue(value = "${pwd.prop.info} ${pwd.yml.info}", autoRefreshed = true)
    private String info;

    /**
     * 不加autoRefreshed = true没法动态刷新
     */
    @NacosValue("${pwd.yml.num:18}")
    private Integer num;

    @GetMapping("config")
    public Object sayHello(HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("info", info);
        resultMap.put("num", num);
        return resultMap;
    }

}
