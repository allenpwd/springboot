package pwd.allen.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author allen
 * @create 2023-03-21 21:53
 **/
@SpringBootTest(classes = ValueTest.class, properties = {"test.myStr=fuck"}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ValueTest {

    /**
     * @Value属性名支持驼峰或者-分隔的方式
     *
     * 解析时机：
     *  解析占位符是 {@link org.springframework.beans.factory.support.AbstractBeanFactory#resolveEmbeddedValue(String)}
     *  解析spel是 {@link org.springframework.beans.factory.support.AbstractBeanFactory#evaluateBeanDefinitionString(String, org.springframework.beans.factory.config.BeanDefinition)}
     *
     * 原理：引入了{@link org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean}，会在bean初始化属性的时候回调
     *  {@link org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, Object, String)}
     *  然后委托给InjectedElement处理，有两个内部类的实现：{@link org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement}和AutowiredMethodElement。
     *
     */
    @Value("${test.my-str:default}")
    private String mystr;

    /**
     * StringValueResolver初始化时机
     *      默认是spring内置的：{@link AbstractApplicationContext#finishBeanFactoryInitialization(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
     *      如果声明一个PropertySourcesPlaceholderConfigurer的话，初始化时机则是
     *      -》{@link AbstractApplicationContext#invokeBeanFactoryPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)}
     *      -》{@link org.springframework.context.support.PropertySourcesPlaceholderConfigurer#postProcessBeanFactory(ConfigurableListableBeanFactory)}
     *
     * TODO 为何不声明为static也行
     *
     * @return
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Test
    public void test() {
        System.out.println(mystr);
    }
}
