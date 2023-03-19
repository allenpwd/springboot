package pwd.allen.task;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import pwd.allen.config.CustomTaskScheduler;

/**
 * 按条件自动停止任务
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AutoStopTask {

    private final CustomTaskScheduler taskScheduler;

    private int count;

    /**
     * 每执行3次后 停止调度，然后再重新开启调度
     */
    @Scheduled(cron = "*/3 * * * * *")
    public void printTask() {
        log.info("公众号Java技术栈，任务执行次数：{}", count + 1);

        count++;

        // 执行3次后自动停止
        if (count >= 3) {
            log.info("任务已执行指定次数，现在自动停止");
            boolean cancelled = taskScheduler.getScheduledTasks().get(this).cancel(true);
            if (cancelled) {
                count = 0;
                ScheduledMethodRunnable runnable = new ScheduledMethodRunnable(this, ReflectionUtils.findMethod(this.getClass(), "printTask"));
                taskScheduler.schedule(runnable, new CronTrigger("*/3 * * * * *"));
            }
        }
    }

}
