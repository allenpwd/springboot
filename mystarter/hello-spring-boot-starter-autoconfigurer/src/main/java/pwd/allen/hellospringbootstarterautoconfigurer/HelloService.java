package pwd.allen.hellospringbootstarterautoconfigurer;

import lombok.Data;

/**
 * @author lenovo
 * @create 2019-09-02 14:48
 **/
@Data
public class HelloService {

    private HelloProperties helloProperties;

    public String getHelloStr(String name) {
        return helloProperties.getPrefix() + name + helloProperties.getSuffix();
    }
}
