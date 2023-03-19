package pwd.allen.task;


import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单计划任务
 * 公众号：Java技术栈
 */
@Slf4j
@Component
public class SimpleTask {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 循环依赖的处理方式：
     *  方式一：加上@Lazy
     *  方式二：使用ObjectProvider，不过调用时需要显式通过ObjectProvider.getIfAvailable()
     */
    @Lazy
    @Autowired
    private SimpleTask simpleTask;

    private AtomicInteger integer = new AtomicInteger(0);

    private static final ThreadLocal<Object> THREAD_LOCAL = new TransmittableThreadLocal<>();

    @Scheduled(cron = "*/5 * * * * *")
    public void printTask() {
        log.info("定时调起的任务开始");
        THREAD_LOCAL.set(integer.incrementAndGet());
        // 直接调是不是无法异步
        simpleTask.async();
        log.info("定时调起的任务结束");
    }

    @Async
    public void async() {
        log.info("【{}】变量：{}", Thread.currentThread().getName(), THREAD_LOCAL.get());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error(e.toString(), e);
        }
    }

}
