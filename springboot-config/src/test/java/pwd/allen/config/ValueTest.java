package pwd.allen.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * 测试下@Value没解析的问题
 * @author allen
 * @create 2023-03-21 21:53
 **/
@SpringBootTest(classes = {ValueTest.class, TestConfig.class}, properties = {"test.myStr=fuck"}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ValueTest {

    @Autowired
    private Environment environment;

    @Test
    public void test() {
        System.out.println(environment.resolvePlaceholders("test.my-str=${test.myStr}"));
    }
}

@Configuration
class TestConfig {

    public TestConfig() {
        System.out.println("init TestConfig");
    }

    @Value("${test.my-str:default}")
    private String mystr;

    /**
     * StringValueResolver初始化时机
     *      默认是spring内置的：{@link AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
     *      如果声明一个PropertySourcesPlaceholderConfigurer的话，初始化时机则是
     *      -》{@link AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
     *      -》{@link org.springframework.context.support.PropertySourcesPlaceholderConfigurer#postProcessBeanFactory(ConfigurableListableBeanFactory)}
     *
     * 建议声明时方法加上static，因为这样实例化PropertySourcesPlaceholderConfigurer的时候不需要先实例化其所在的Config类
     * 如果提前实例化Config类，可能导致Config的@Value解析不到
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 测试一下提前实例化
     * @return
     */
    @Bean
    public BeanPostProcessor myBeanPostProcessor() {
        // 如果没有声明PropertySourcesPlaceholderConfigurer或者声明时不是static，这里获取不到myStr
        System.out.println(mystr);
        return new BeanPostProcessor() {};
    }
}