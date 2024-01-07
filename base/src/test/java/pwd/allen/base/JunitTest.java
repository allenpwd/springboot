package pwd.allen.base;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lenovo
 * @create 2020-01-23 17:45
 **/
public class JunitTest {

    @RepeatedTest(5)
    public void repeat() {
        System.out.println("hello!");
    }

    @Test
    public void test() {
        Matcher feature = Pattern.compile("(feature|master)").matcher("feature-1.0.0.pwdan#123");
        if (feature.matches()) {
            System.out.println(feature.group(0));
        }
    }

    /**
     * {@link SpringFactoriesLoader#FACTORIES_RESOURCE_LOCATION}
     * @throws IOException
     */
    @Test
    public void loader() throws IOException {
        // 获取spring.factories中配置的指定类
        List<String> stringList = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, getClass().getClassLoader());
        System.out.println(stringList);
    }

    /**
     * 读取yml文件
     * @throws IOException
     */
    @Test
    public void readConfig() throws IOException {
        // 获取Spring的环境对象
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        // 读取yml文件并添加到environment中
        List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load("application.yml", new DefaultResourceLoader().getResource("application.yml"));
        propertySourceList.forEach(propertySources::addLast);
        System.out.println(environment.getProperty("server.port"));
    }
}
