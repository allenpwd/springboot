package pwd.allen.config;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringValueResolver;

/**
 * 测试一下config
 *
 * @PropertySource：能将指定的配置放入spring的environment中
 * 原理：beanFactory后置处理器 {@link ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 *
 * @author pwdan
 * @create 2023-03-17 17:32
 **/
@PropertySource(value = {"classpath:test.properties"})
@Configuration
@ContextConfiguration(classes = {ConfigTest.class})
public class ConfigTest implements EmbeddedValueResolverAware {

    /**
     * 任意@Companent或JSR-330注解的类都可以作为AnnotationConfigApplicationContext构造方法的输入
     */
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private StringValueResolver valueResolver;

    @Data
    class TestBean {
        @Value("${my.prop1:default} #{1+3}")
        private String prop1;
    }
    @Bean
    public TestBean testBean() {
        return new TestBean();
    }

    /**
     * @Value的处理器StringValueResolver初始化时机是
     *      -》{@link org.springframework.context.support.PropertySourcesPlaceholderConfigurer#postProcessBeanFactory(ConfigurableListableBeanFactory)}
     *
     * 处理@Value属性解析的时机是（关键是AutowiredAnnotationBeanPostProcessor这个bean后置处理器）
     *      -》{@link AbstractAutowireCapableBeanFactory#populateBean(String, org.springframework.beans.factory.support.RootBeanDefinition, org.springframework.beans.BeanWrapper)}
     *      -》{@link AutowiredAnnotationBeanPostProcessor#postProcessProperties(org.springframework.beans.PropertyValues, Object, String)}
     *      -》{@link org.springframework.beans.factory.support.AbstractBeanFactory#resolveEmbeddedValue(String)}
     *      -》{@link DefaultListableBeanFactory#resolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, String, java.util.Set, org.springframework.beans.TypeConverter)}
     *          其中，解析占位符是 {@link AbstractBeanFactory#resolveEmbeddedValue(String)}
     *          解析spel是 {@link AbstractBeanFactory#evaluateBeanDefinitionString(String, org.springframework.beans.factory.config.BeanDefinition)}
     */
    @Test
    public void resolveValue() {
        String str = new StringBuilder("#{'os.name=${os.name}'}\n")
                .append("my.prop2=${my.prop2}\n")
                .append("my.noExist=${my.noExist}\n")
                .append("算术：#{12+35}\n")
                .append("读取bean属性：#{'testBean.prop1='+testBean.prop1}\n")
                .append("systemEnvironment：#{systemEnvironment['user.dir']}\n")
                .append("systemProperties：#{systemProperties['user.dir']}\n")
                .append("user.dir=${user.dir}\n")
                .toString();

        //使用beanFactory解析属性中的占位符
        System.out.println(applicationContext.getBeanFactory().resolveEmbeddedValue(str));

        //EmbeddedValueResolver解析属性中的占位符和spel表达式，先使用beanFactory解析占位符，再使用BeanExpressionResolver解析spel表达式
        System.out.println(valueResolver.resolveStringValue(str));
    }

    /**
     * EmbeddedValueResolverAware：注入进来一个StringValueResolver，用于占位符解释、SpEL计算
     * @param resolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        valueResolver = resolver;
    }
}
