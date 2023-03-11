package pwd.allen;

import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
}
