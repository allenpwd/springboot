package pwd.allen;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author allen
 * @create 2023-03-19 21:47
 **/
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/log")
    public Object test(@RequestParam Map<String, Object> mapParam) {
        log.info("param:{}", JSONUtil.toJsonPrettyStr(mapParam));
        return mapParam;
    }
}
