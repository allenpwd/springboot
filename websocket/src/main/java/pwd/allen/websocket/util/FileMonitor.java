package pwd.allen.websocket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pwdan
 * @create 2022-05-30 9:58
 **/
public class FileMonitor {

    private static final Logger log = LoggerFactory.getLogger(FileMonitor.class);

    /**
     * 绑定的websocket
     */
    private String sessionId;

    /**
     * 绑定的监控日志路径
     */
    private String logPath;

    /**
     * 监控时间间隔，单位ms
     */
    private Long monitorDelay;

    public FileMonitor(String sessionId, String logPath) {
        this(sessionId, logPath, 500L);
    }

    public FileMonitor(String sessionId, String logPath, Long monitorDelay) {
        this.sessionId = sessionId;
        this.logPath = logPath;
        this.monitorDelay = monitorDelay;
        startFileMonitor(monitorDelay);
    }

    private void startFileMonitor(Long monitorDelay) {
        Thread thread = new Thread(new FileMonitorRunnable(sessionId, logPath, monitorDelay));
        thread.start();
    }
}
