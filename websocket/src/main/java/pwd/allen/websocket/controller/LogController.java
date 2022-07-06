package pwd.allen.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.websocket.util.WebSocketUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author pwdan
 * @create 2022-07-06 16:30
 **/
@Slf4j
@RequestMapping("log")
@RestController
public class LogController {

    /**
     * 下载文件
     * @param response
     */
    @RequestMapping("/download")
    public Object download(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> mapParam = WebSocketUtils.decQuery(request.getQueryString());
        InputStream inputStream = null;
        try {
            // path是指想要下载的文件的路径
            String path = mapParam.get("path");
            File file = new File(path);

            if (!file.exists() || !file.isFile()) {
                return "文件不存在";
            }
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件名：{}，文件后缀名：{}", filename, ext);

            // 读到流中
            inputStream = new FileInputStream(path);// 文件的存放路径
            response.reset();
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] b = new byte[1024 * 10];
            int len;
            //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            outputStream.flush();
        } catch (IOException ex) {
            log.error(ex.toString(), ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
        }
        return null;
    }

}
