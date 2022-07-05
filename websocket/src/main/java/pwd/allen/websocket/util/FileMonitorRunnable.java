package pwd.allen.websocket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.util.Length;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * @author pwdan
 * @create 2022-05-30 9:59
 **/
public class FileMonitorRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FileMonitorRunnable.class);

    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 100);

    private CharBuffer charBuffer = CharBuffer.allocate(1024 * 50);

    private CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

    private boolean isRunning = true;

    private String sessionId;

    private String logPath;

    /**
     * 同步内容的间隔，单位毫秒
     */
    private Long monitorDelay;

    public FileMonitorRunnable(String sessionId, String logPath, Long monitorDelay) {
        this.sessionId = sessionId;
        this.logPath = logPath;
        this.monitorDelay = monitorDelay;
    }

    @Override
    public void run() {
        File file = new File(logPath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel channel = fileInputStream.getChannel();
            long lastModified = file.lastModified();
            //TODO: 初次连接将所有内容丢回去？这个考虑到数据如果很多先不丢
            int length = 1024 * 10;

            if (channel.size() > 0) {
                channel.position(channel.size() > length ? channel.size() - length : 0);
                channel.read(byteBuffer);
                byteBuffer.flip();
                try {
                    while (byteBuffer.get() != 10) {}
                    decoder.decode(byteBuffer, charBuffer, true);
                    charBuffer.flip();
                    WebSocketUtils.sendMessageTo(sessionId, charBuffer.toString());
                } catch (BufferUnderflowException e) {
                    e.printStackTrace();
                }
            }

            while (isRunning) {
                long now = file.lastModified();
                if (now != lastModified) {
//                    log.info("{}的连接正在通过线程{}监控{}的文件update", sessionId, Thread.currentThread().getName(), logPath);
                    String newContent = getNewContent(channel);
                    WebSocketUtils.sendMessageTo(sessionId, newContent);
                    lastModified = now;
                }
                Thread.sleep(monitorDelay);
                isRunning = WebSocketUtils.currentSessionAlive(sessionId);
            }
            channel.close();
        } catch (Exception e) {
            log.error("监控文件失败，检查路径是否正确", e);
            WebSocketUtils.endMonitor(sessionId);
            e.printStackTrace();
        }
    }

    private String getNewContent(FileChannel channel) throws IOException {
        byteBuffer.clear();
        charBuffer.clear();
        int length = channel.read(byteBuffer);
        if (length != -1) {
            byteBuffer.flip();
            decoder.decode(byteBuffer, charBuffer, true);
            charBuffer.flip();
            return charBuffer.toString();
        } else {
            channel.position(channel.size());
        }
        return null;
    }
}
