package pwd.allen.base.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pwd.allen.base.entity.AlarmMessage;
import pwd.allen.base.entity.MyResult;

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
//@ConditionalOnExpression(value = "${controller.MyController.enabled}==true")    //开启的条件是：controller.MyController.enabled=true
@ConditionalOnProperty(prefix = "controller.MyController", value = "enabled", havingValue = "true", matchIfMissing = true)    // 默认也会开启
@RequestMapping("my")
@RestController
@Api(tags = "我的控制器")
@ApiSupport(author = "门那粒沙")
@Slf4j
public class MyController {

    @Autowired
    private Knife4jProperties knife4jProperties;

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
        req.getRequestDispatcher("/my/alarmMessage").forward(req, resp);
        return "forward test";
    }

    @ApiOperation("alarmMessage")
    @PostMapping("alarmMessage")
    public MyResult<AlarmMessage> log(AlarmMessage alarmMessage) {
        log.info("info");
        log.warn("warn");
        log.debug("debug");
        return MyResult.success(alarmMessage);
    }

    @Value("${uploadPath:/opt/IBM/ABC/test/file/}")
    private String uploadPath;

    @ApiOperation(value = "上传", notes = "上传")
    @ApiOperationSupport(author = "门那粒沙", order = 10)
    @PostMapping("upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws IOException {
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
