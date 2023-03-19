package pwd.allen.task;


import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 简单计划任务
 * 公众号：Java技术栈
 */
@Slf4j
@Component
public class SimpleTask {

    @Autowired
    ApplicationContext applicationContext;

    private static final ThreadLocal<Object> THREAD_LOCAL = new TransmittableThreadLocal<>();

    @Scheduled(cron = "*/5 * * * * *")
    public void printTask() {
        log.info("定时调起的任务开始");
        THREAD_LOCAL.set(new Date());
        // 直接调是不是无法异步
        applicationContext.getBean(SimpleTask.class).async();
        log.info("定时调起的任务结束");
    }

    @Async
    public void async() {
        log.info("【{}】从父线程获取的变量：{}", Thread.currentThread().getName(), THREAD_LOCAL.get());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            log.error(e.toString(), e);
        }
    }

}
