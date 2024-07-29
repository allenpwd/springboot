import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pwd.allen.redis.entity.User;

import java.util.Date;

/**
 * @author pwdan
 * @create 2024-07-29 15:58
 **/
public class RedisSerializerTest {

    private User user;

    @BeforeEach
    public void init() {
        user = new User("name", 18, new Date());
    }

    @Test
    public void FastJsonRedisSerializer() {
//        FastJsonRedisSerializer<Object> redisSerializer = new FastJsonRedisSerializer<>(Object.class);
        GenericFastJsonRedisSerializer redisSerializer = new GenericFastJsonRedisSerializer();

        byte[] serialize = redisSerializer.serialize(user);
        System.out.println(new String(serialize));

        System.out.println(redisSerializer.deserialize(serialize));
    }

}
