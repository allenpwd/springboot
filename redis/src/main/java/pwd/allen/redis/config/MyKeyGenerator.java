package pwd.allen.redis.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义缓存key的生成策略
 * @author allen
 * @create 2022-07-03 22:06
 **/
@Component
public class MyKeyGenerator implements KeyGenerator {

    /**
     *
     * @param target 调用类
     * @param method 调用方法
     * @param params 调用参数
     * @return
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
            sb.append(obj.toString());
        }
        return sb.toString();
    }
}
