package pwd.allen.base.util;

import org.junit.jupiter.api.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.scheduling.annotation.Async;

/**
 * @author allen
 * @create 2023-03-19 16:43
 **/
@Async(value = "executorName")
public class ReflectTest {

    /**
     * 获取注解
     */
    @Test
    public void test() {
        Async async = AnnotatedElementUtils.findMergedAnnotation(ReflectTest.class, Async.class);
        System.out.println(async.value());
    }
}
