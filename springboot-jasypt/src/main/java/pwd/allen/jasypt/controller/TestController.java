package pwd.allen.jasypt.controller;

import cn.hutool.core.map.MapUtil;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${pwd.password}")
    private String password;

    @Autowired
    private StringEncryptor encryptor;
    @GetMapping("/info")
    public Object info() {
        return MapUtil.<String, Object>builder().put("password", password).build();
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String text) {
        return encryptor.encrypt(text);
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String text) {
        return encryptor.decrypt(text);
    }
}
