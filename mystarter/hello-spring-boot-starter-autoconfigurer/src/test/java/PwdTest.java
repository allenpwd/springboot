import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * @author allen
 * @create 2023-02-05 15:45
 **/
public class PwdTest {

    @Test
    public void spi() {
        // getCandidateConfigurations方法会从META-INF/spring.factories中获取各个组件的自动配置类的全限定名
        List<String> stringList = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, this.getClass().getClassLoader());
        System.out.println(stringList);
    }
}
