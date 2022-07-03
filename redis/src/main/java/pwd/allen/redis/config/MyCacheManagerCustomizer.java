package pwd.allen.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

/**
 * 自定义修改CacheManager（没什么用）
 *
 * @author allen
 * @create 2022-07-03 17:44
 **/
@Slf4j
@Component
public class MyCacheManagerCustomizer implements CacheManagerCustomizer<RedisCacheManager> {
    @Override
    public void customize(RedisCacheManager cacheManager) {
        log.info("做自定义操作");
//        cacheManager.setTransactionAware(true);
    }
}
