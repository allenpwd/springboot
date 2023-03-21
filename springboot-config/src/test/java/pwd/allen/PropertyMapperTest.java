package pwd.allen.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.PropertyMapper;
import pwd.allen.property.MyProperties;

/**
 * 是Spring Boot框架中的一个工具类，用于将配置属性映射到Java类的属性上
 *
 * 缺点：
 *  内存/时间开销：由于每次调用方法都返回一个新的 PropertyMapper 实例，在压测中有明显的性能影响 (对于附录的代码，常规与简化方式的执行时间对比是 7ms:48ms）
 *  同一个对象不可复用：从上边的例子中可以看到，对于常规的 if 判断，后边都可以享受到判断结果，但是在 PropertyMapper 中一般则不可，套用则有可能影响阅读(可看后边的代码)。
 *
 * @author allen
 * @create 2023-03-18 20:18
 **/
public class PropertyMapperTest {

    private MyProperties myProperties = new MyProperties();
    private PropertyMapper propertyMapper = PropertyMapper.get();

    /**
     * 设置属性
     */
    @Test
    public void set() {
        // alwaysApplyingWhenNonNull不加的话，属性为空会抛出空指针异常，等同于每个赋值都加上.whenNonNull()
        propertyMapper = propertyMapper.alwaysApplyingWhenNonNull();

        // 设置myStr
        Object str = null;
        propertyMapper.from(str)
                .whenInstanceOf(String.class)  // 为字符串类型才做后续处理，并转成String类型
                .when(x -> x.length() > 2)  // 长度大于2才赋值
                .as(x -> x + "haha")    // 字符串加工
                .to(myProperties::setMyStr);

        propertyMapper.from("12")
//                .whenNonNull() // 不为空才做后续处理
                .as(Integer::parseInt)
                .to(myProperties::setInteger);

        // 如果myProperties不为空，且其myStr属性不为空，则打印
        propertyMapper.from(myProperties)
                .as(MyProperties::getMyStr)
                .to(System.out::print);

        System.out.println(myProperties);
    }

}
