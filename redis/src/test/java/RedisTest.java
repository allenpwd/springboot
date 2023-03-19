import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.redis.RedisMain;
import pwd.allen.redis.entity.User;
import pwd.allen.redis.service.MyService;
import pwd.allen.redis.service.RedisLockService;
import pwd.allen.redis.service.RedisOptService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author allen
 * @create 2022-07-03 13:21
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMain.class)
public class RedisTest {

    @Autowired
    private MyService myService;

    @Autowired
    private RedisOptService redisOptService;

    @Autowired
    private RedisLockService redisLockService;

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
        redisOptService.set("pwd:test", entity);
        Object o = redisOptService.get("pwd:test");
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

    /**
     * 测试redis分布式锁
     * @throws InterruptedException
     */
    @Test
    public void lock() throws InterruptedException {
        String lockKey = "pwd";

        Runnable runnable = () -> {
            log.info("【{}】尝试获取锁", Thread.currentThread().getName());
            redisLockService.lock(lockKey);
            log.info("【{}】成功获取锁", Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            redisLockService.unlock(lockKey);
            log.info("【{}】成功释放锁", Thread.currentThread().getName());
        };

        new Thread(runnable, "thread1").start();
        new Thread(runnable, "thread2").start();
        new Thread(runnable, "thread3").start();

        Thread.sleep(100);
        log.info("tryLock：" + redisLockService.tryLock(lockKey));
        log.info("主程序开始阻塞方式获取锁");
        redisLockService.lock(lockKey);
        log.info("主程序成功获取锁");
        redisLockService.unlock(lockKey);
        log.info("主程序成功释放锁");
    }
}
