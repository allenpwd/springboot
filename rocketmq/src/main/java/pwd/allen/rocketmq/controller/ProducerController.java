package pwd.allen.rocketmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.rocketmq.service.LocalService;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:22
 */
@Slf4j
@RestController
public class ProducerController {

    @Autowired
    LocalService localService;

    @GetMapping("/product/send/{s}")
    public String sendSyncMessage(@PathVariable("s")String s){
        return localService.sentSyncMessage(s);
    }

}
