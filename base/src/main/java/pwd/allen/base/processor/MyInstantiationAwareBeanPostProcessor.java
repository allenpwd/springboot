package pwd.allen.base.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import pwd.allen.base.entity.MyBean;

/**
 * BeanPostProcessor的拓展，增加了postProcessProperties
 *
 * spring中的实现例子
 *  {@link org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor} 处理@Value @Autowired注入
 *
 * @author pwdan
 * @create 2023-03-22 11:25
 **/
@Slf4j
@Component
public class MyInstantiationAwareBeanPostProcessor extends MyBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    /**
     * 执行时机：
     *  -》{@link AbstractAutowireCapableBeanFactory#populateBean}
     *
     * @param pvs  工厂将要应用的属性值(从不为空)
     * @param bean 已创建的Bean实例，但其属性尚未设置
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (bean instanceof MyBean) {

        }
        return null;
    }
}
