package pwd.allen.config;

import lombok.Data;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * 重写spring的任务调度所有 schedule* 方法，记录调度对象，便于后续操作：例如取消定时任务
 */
@Data
public class CustomTaskScheduler extends ThreadPoolTaskScheduler {

    private Map<Object, ScheduledFuture<?>> scheduledTasks = new IdentityHashMap<>();

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        ScheduledFuture<?> future = super.schedule(task, trigger);
        this.putScheduledTasks(task, future);
        return future;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Duration period) {
        ScheduledFuture<?> future = super.scheduleAtFixedRate(task, period);
        this.putScheduledTasks(task, future);
        return future;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Instant startTime, Duration period) {
        ScheduledFuture<?> future = super.scheduleAtFixedRate(task, startTime, period);
        this.putScheduledTasks(task, future);
        return future;
    }

    private void putScheduledTasks(Runnable task, ScheduledFuture<?> future) {
        ScheduledMethodRunnable runnable = (ScheduledMethodRunnable) task;
        scheduledTasks.put(runnable.getTarget(), future);
    }
}