package pwd.allen.base.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import pwd.allen.base.entity.MyBean;

/**
 * spring中的实现例子
 *  {@link org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor}:处理@PostConstruct和@PreDestroy的
 *
 * @author pwd
 * @create 2018-11-03 19:43
 **/
@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 执行时机：bean实例化并且填充完之后，调用{@link AbstractAutowireCapableBeanFactory#invokeInitMethods} 之前
     * @param o
     * @param s
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        if (o instanceof MyBean) {
            log.info("调用BeanPostProcessor的postProcessBeforeInitialization方法，beanName:{}", s);
        }
        return o;
    }

    /**
     * 执行时机：调用{@link AbstractAutowireCapableBeanFactory#invokeInitMethods} 之后
     * @param o
     * @param s
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof MyBean) {
            log.info("调用BeanPostProcessor的postProcessAfterInitialization方法，beanName:{}", s);
        }
        return o;
    }
}
