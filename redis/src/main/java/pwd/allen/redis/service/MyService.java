package pwd.allen.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.stereotype.Service;
import pwd.allen.redis.entity.User;

import java.util.Date;

/**
 * @see CacheAspectSupport#execute(org.springframework.cache.interceptor.CacheOperationInvoker, java.lang.reflect.Method, org.springframework.cache.interceptor.CacheAspectSupport.CacheOperationContexts)
 *
 * @author allen
 * @create 2022-07-03 17:52
 **/
@Slf4j
@Service
public class MyService {

    /**
     * Cacheable注解表示先从缓存中通过定义的键值进行查询，如果查询不到则进行数据库查询并将查询结果保存到缓存中，其中：
     * value属性为spring application.properties配置文件中设置的缓存名称
     * key表示缓存的键值名称，其中id说明该方法需要一个名为id的参数
     * condition ：可以用来指定符合条件的情况下才缓存,不能访问#result
     * unless:否定缓存。当 unless 指定的条件为 true ，方法的返回值就不会被缓存
     * keyGenerator ：key 的生成器。 key 和 keyGenerator 二选一使用
     * @param name
     * @return
     */
    @Cacheable(value = "redisCache", key = "#root.methodName + ':' + #name", sync = false, unless = "#result == null")
    public User getUser(String name) {
        return new User(name, name.length() + 18, new Date());
    }

    /**
     * CachePut注解表示将方法的返回结果存放到缓存中
     *
     * @see pwd.allen.redis.config.MyRedisCacheManagerBuilderCustomizer 自定义缓存配置，使用json序列化
     * @see pwd.allen.redis.config.MyKeyGenerator 自定义key生成策略
     * @param user
     * @return
     */
    @CachePut(value = "myRedisCache", keyGenerator = "myKeyGenerator", unless = "#result == null")
    public User putUser(User user) {
        user.setName("更新：" + user.getName());
        user.setBirthday(new Date());
        log.info("操作了数据库，添加了{}", user);
        return user;
    }

    @CacheEvict(value = "myRedisCache", key = "#name")
    public Boolean deleteUser(String name) {
        log.info("操作了数据库，删除了{}", name);
        return true;
    }
}
