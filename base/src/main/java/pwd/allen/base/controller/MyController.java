package pwd.allen.base.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pwd.allen.base.entity.AlarmMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author pwd
 * @create 2019-02-14 16:03
 **/
//@ConditionalOnExpression("${controller.MyController.enabled}==true")    //开启的条件是：controller.MyController.enabled=true
@ConditionalOnProperty(prefix = "controller.MyController", value = "enabled", matchIfMissing = true)    //默认也会开启
@RequestMapping("my")
@RestController
@Api(tags = "我的控制器")
@Slf4j
public class MyController {

    /**
     * 转发
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("forward")
    public Object forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/my/log").forward(req, resp);
        return "forward test";
    }

    @GetMapping("log")
    public Object log() {
        log.info("info");
        log.warn("warn");
        log.debug("debug");
        return new AlarmMessage();
    }

    @Value("${uploadPath:/opt/IBM/ABC/test/file/}")
    private String uploadPath;

    @PostMapping("upload")
    public Object upload(MultipartHttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("size", file.getSize());
        map.put("originalFilename", file.getOriginalFilename());
        map.put("type", "success");
        String str_date = DateUtil.format(new Date(), "yyyyMMdd");
        File saveFile = new File(String.format("%s/%s/%s", uploadPath, str_date, file.getOriginalFilename()));
        FileUtil.mkParentDirs(saveFile);
        // 用这个报错说找不到路径
//        file.transferTo(saveFile);
        try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
            FileCopyUtils.copy(file.getInputStream(), fileOutputStream);
        }
        map.put("path", saveFile.getAbsolutePath());
        return map;
    }

}