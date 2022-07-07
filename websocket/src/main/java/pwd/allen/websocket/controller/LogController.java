package pwd.allen.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.websocket.entity.FileInfo;
import pwd.allen.websocket.util.WebSocketUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * 列出目录
     * @param response
     */
    @RequestMapping("/ls")
    public Object ls(HttpServletRequest request, HttpServletResponse response) {
        List<FileInfo> list = new ArrayList<>();
        Map<String, String> mapParam = WebSocketUtils.decQuery(request.getQueryString());
        // path是指想要下载的文件的路径
        String path = mapParam.get("path");
        String bOnlyDir = mapParam.get("bOnlyDir");

        File filePath = new File(path);

        if (!filePath.exists() || !filePath.isDirectory()) {
            return "目录不存在";
        }
        File[] files = filePath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if ("1".equals(bOnlyDir) && pathname.isFile()) {
                    return false;
                }
                return true;
            }
        });
        if (files != null && files.length > 0) {
            for (File file : files) {
                list.add(new FileInfo(file.getAbsolutePath(), file.getName(), new Date(file.lastModified()), (file.isFile() ? file.length() : null), file.isFile()));
            }
        }
        return list;
    }

}
