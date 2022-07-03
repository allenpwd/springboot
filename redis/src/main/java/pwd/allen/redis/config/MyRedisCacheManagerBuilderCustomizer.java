package pwd.allen.redis.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 自定义修改CacheManager
 * @author allen
 * @create 2022-07-03 22:18
 **/
@Component
public class MyRedisCacheManagerBuilderCustomizer implements RedisCacheManagerBuilderCustomizer {

    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        // 自定义一个redisCacheConfiguration，使用json序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)))
                .prefixKeysWith("myRedisCache:")
                // 设置失效时间
                .entryTtl(Duration.ofMinutes(10));
        builder.withCacheConfiguration("myRedisCache", config);
    }
}
