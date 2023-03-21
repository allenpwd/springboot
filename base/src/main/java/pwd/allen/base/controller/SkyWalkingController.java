package pwd.allen.base.controller;

import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.web.bind.annotation.*;
import pwd.allen.base.entity.AlarmMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * skywalking告警挂钩方法
 * @author 门那粒沙
 * @create 2021-03-07 17:51
 **/
@RestController
@RequestMapping("sw")
public class SkyWalkingController {

    /**
     * 记录最后一次skyWalking推送过来的告警信息
     */
    private List<AlarmMessage> lastList = new ArrayList<>();

    /**
     * 根据traceId能在skyWalking UI的链路追踪模块中搜索到具体链路
     *
     * @return
     */
    @GetMapping("/traceId")
    public Object skywalking() {
        //使当前链路报错，并提示报错信息
        ActiveSpan.error(new RuntimeException("测试下在当前链路中报错"));

        // 打印info信息
        ActiveSpan.info("测试打印Info信息");

        // 打印debug信息
        ActiveSpan.debug("测试打印debug信息");

        return TraceContext.traceId();
    }

    @PostMapping("/webhook")
    public void webhoook(@RequestBody List<AlarmMessage> list) {
        lastList = list;
    }

    @GetMapping("/show")
    public List<AlarmMessage> show() {
        return lastList;
    }
}
