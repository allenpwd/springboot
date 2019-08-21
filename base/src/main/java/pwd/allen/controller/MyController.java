package pwd.allen.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.property.MyProperties;

import java.util.Map;

/**
 * @author pwd
 * @create 2019-02-14 16:03
 **/
@RequestMapping("my")
@RestController
public class MyController {

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private MyProperties myProperties;

    @RequestMapping(value = "myConfig", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object showMyConfig(@RequestParam Map paramMap) {

        logger.info("paramMap {}", paramMap);

        return myProperties;
    }

}
