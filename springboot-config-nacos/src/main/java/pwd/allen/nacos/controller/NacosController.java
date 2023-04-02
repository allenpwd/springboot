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

    /**
     * 只能使用一个占位符，因为多个占位符的话只会用第一个占位符的属性名作为key，也就是只有当第一个占位符的属性值改变了才会刷新
     * 获取key的逻辑：{link com.alibaba.nacos.spring.context.annotation.config.NacosValueAnnotationBeanPostProcessor#resolvePlaceholder}
     */
//    @NacosValue(value = "${pwd.prop.info} ${pwd.yml.info}", autoRefreshed = true) //别用多个占位符，这种写法pwd.yml.info改变了不会动态刷新
    @NacosValue(value = "${pwd.prop.info}", autoRefreshed = true)
    private String propInfo;

    @NacosValue(value = "${pwd.yml.info}", autoRefreshed = true)
    private String ymlInfo;

    /**
     * 不加autoRefreshed = true没法动态刷新
     */
    @NacosValue("${pwd.yml.num:18}")
    private Integer num;

    @GetMapping("config")
    public Object sayHello(HttpServletRequest request) {
        HashMap<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("propInfo", propInfo);
        resultMap.put("ymlInfo", ymlInfo);
        resultMap.put("num", num);
        return resultMap;
    }

}
