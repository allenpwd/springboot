package pwd.allen.websocket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author pwdan
 * @create 2022-05-30 9:59
 **/
public class FileMonitorRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FileMonitorRunnable.class);

    /**
     * 缓存数据进行数据处理
     */
    private ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 100);

    /**
     * 缓存处理后的数据，遇到换行则返回一整行数据并重新缓存
     */
    private ByteBuffer lineBuffer = ByteBuffer.allocate(1024);

    private boolean isRunning = true;

    private String sessionId;

    private Charset charset = Charset.defaultCharset();
    private int loadSize = 1024 * 10;

    /**
     * ws请求上携带的参数
     */
    private Map<String, String> mapParam;

    /**
     * 同步内容的间隔，单位毫秒
     */
    private Long monitorDelay;

    public FileMonitorRunnable(String sessionId, Map<String, String> mapParam, Long monitorDelay) {
        this.sessionId = sessionId;
        this.mapParam = mapParam;
        this.monitorDelay = monitorDelay;
        try {
            if (mapParam.containsKey("loadSize")) {
                this.loadSize = Integer.parseInt(this.mapParam.get("loadSize"));
            }
            if (mapParam.containsKey("charset")) {
                this.charset = Charset.forName(this.mapParam.get("charset"));
            }
        } catch (Exception e) {}

    }

    @Override
    public void run() {
        File file = new File(mapParam.get("logPath"));
        try (FileInputStream fileInputStream = new FileInputStream(file);FileChannel channel = fileInputStream.getChannel()) {
            long lastModified = file.lastModified();

            //<editor-fold desc="初始时读取要多少内容">
            if (channel.size() > 0) {
                if (loadSize > 0) {
                    channel.position(channel.size() > loadSize ? channel.size() - loadSize : 0);
                }
                readLines(channel, (list) -> {
                    StringBuilder sb = new StringBuilder();
                    for (String str : list) {
                        sb.append(str);
                    }
                    WebSocketUtils.sendMessageTo(sessionId, sb.toString());
                });
            }
            //</editor-fold>

            while (isRunning) {
                long now = file.lastModified();
                if (now != lastModified) {
                    readLines(channel, (list) -> {
                        StringBuilder sb = new StringBuilder();
                        for (String str : list) {
                            sb.append(str);
                        }
                        WebSocketUtils.sendMessageTo(sessionId, sb.toString());
                    });
                    lastModified = now;
                }
                Thread.sleep(monitorDelay);
                isRunning = WebSocketUtils.currentSessionAlive(sessionId);
            }
        } catch (Exception e) {
            log.error("监控文件失败，检查路径是否正确", e);
            WebSocketUtils.endMonitor(sessionId);
            e.printStackTrace();
        }
    }


    private void readLines(FileChannel channel, Consumer<List<String>> consumer) throws IOException {
        // 分批返回，每次处理batchSize行
        int batchSize = 50;
        ArrayList<String> list_rel = new ArrayList<>();
        byteBuffer.clear();
        int bytesRead = channel.read(byteBuffer);
        while (bytesRead != -1) {
            byteBuffer.flip();// 切换模式，写->读
            while (byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                if (b == 10 || b == 13) {
                    // 换行或回车
                    if (lineBuffer.position() == 0) {
                        continue;
                    }
                    lineBuffer.put(b);
                    lineBuffer.flip();
                    // 这里就是一个行
                    String line = charset.decode(lineBuffer).toString();
                    if (!mapParam.containsKey("search") || line.matches(String.format("[\\s\\S]*%s[\\s\\S]*", mapParam.get("search")))) {
                        list_rel.add(line);
                        if (list_rel.size() >= batchSize) {
                            consumer.accept(list_rel);
                            list_rel = new ArrayList<>();
                        }
                    }
                    lineBuffer.clear();
                } else {
                    if (!lineBuffer.hasRemaining()) {
                        // 空间不够扩容
                        lineBuffer = reAllocate(lineBuffer);
                    }
                    lineBuffer.put(b);
                }
            }
            // 清空,position位置为0，limit=capacity
            byteBuffer.clear();
            bytesRead = channel.read(byteBuffer);
        }
        if (!CollectionUtils.isEmpty(list_rel)) {
            consumer.accept(list_rel);
        }
    }

    private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return (ByteBuffer) ByteBuffer.wrap(newBuffer).position(capacity);
    }

}
