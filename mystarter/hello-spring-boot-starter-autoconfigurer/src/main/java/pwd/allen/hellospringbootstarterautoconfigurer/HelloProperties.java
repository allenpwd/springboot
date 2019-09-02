package pwd.allen.hellospringbootstarterautoconfigurer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lenovo
 * @create 2019-09-02 14:48
 **/
@ConfigurationProperties("hello")
@Data
public class HelloProperties {
    private String prefix;
    private String suffix;
}
