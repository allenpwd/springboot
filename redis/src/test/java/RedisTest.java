import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.redis.RedisMain;
import pwd.allen.redis.entity.User;
import pwd.allen.redis.service.MyService;

import java.util.Date;

/**
 * @author allen
 * @create 2022-07-03 13:21
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMain.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private MyService myService;

    /**
     * 如果用GenericJackson2JsonRedisSerializer，存儲的格式是：
     * {
     *     "@class": "RedisTest$Entity",
     *     "name": "springboot",
     *     "age": 20,
     *     "birthday": [
     *         "java.util.Date",
     *         1656836529875
     *     ]
     * }
     *
     * 如果用FastJsonRedisSerializer，存儲的格式是：
     * {
     *     "age": 20,
     *     "birthday": "2022-07-03 16:29:31",
     *     "name": "springboot"
     * }
     */
    @Test
    public void setAndGet() {
        User entity = new User("springboot", 20, new Date());
        redisTemplate.opsForValue().set("pwd:test", entity);
        Object o = redisTemplate.opsForValue().get("pwd:test");
        System.out.println(o);
    }

    @Test
    public void cacheGet() {
        User user = myService.getUser("test");
        System.out.println(user);
    }

    @Test
    public void cacheSet() {
        User entity = new User("test", 20, new Date());
        User user = myService.putUser(entity);
        System.out.println(user);
    }
}
