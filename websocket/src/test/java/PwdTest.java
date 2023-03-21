import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.*;
import pwd.allen.websocket.util.WebSocketUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pwdan
 * @create 2022-07-05 17:23
 **/
public class PwdTest {

    @Test
    public void test() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 100);
        CharBuffer charBuffer = CharBuffer.allocate(1024 * 50);
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

        File file = new File("C:\\Users\\pwdan\\Desktop\\monitor\\test.txt");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel channel = fileInputStream.getChannel();
            long lastModified = file.lastModified();
            //TODO: 初次连接将所有内容丢回去？这个考虑到数据如果很多先不丢
            int length = 1000;
            long channelSize = channel.size();

            if (channelSize > 0) {
                channel.position(channelSize > length ? channelSize - length : 0);
                channel.read(byteBuffer);
                byteBuffer.flip();
                byte b = byteBuffer.get();
                byteBuffer.get();
                byteBuffer.get();

                decoder.decode(byteBuffer, charBuffer, true);
                charBuffer.flip();
                System.out.println(charBuffer.toString());
            }
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String query = "logPath=abc/sdf&name=abc&age=12";
        Map<String, String> mapParam = new HashMap<>();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] split = param.split("=");
                if (split.length == 2) {
                    mapParam.put(split[0], split[1]);
                }
            }
        }
        System.out.println(JSONUtil.toJsonStr(mapParam));
        String test = "202\tAccepted\t已经接受请求，但处理尚未完成。";
    }
}
