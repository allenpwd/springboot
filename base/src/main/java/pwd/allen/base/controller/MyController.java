package pwd.allen.base.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import pwd.allen.base.entity.MyResult;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        req.getRequestDispatcher("/my/myEntity").forward(req, resp);
        return "forward test";
    }

    /**
     * 参数处理逻辑见：
     * {@link InvocableHandlerMethod#getMethodArgumentValues}
     * {@link HandlerMethodArgumentResolverComposite#resolveArgument} 可以打断点在这里（断点条件可以是：），看看参数是由哪个HandlerMethodArgumentResolver处理的
     *
     * @param path 由{@link PathVariableMethodArgumentResolver}处理
     * @param myDate 由{@link RequestParamMethodArgumentResolver}处理
     * @param num 由{@link RequestParamMethodArgumentResolver}处理
     * @param mapParam 类型为map、@RequestParam注释并且没指定value的参数，会被{@link RequestParamMapMethodArgumentResolver}处理
     * @return
     */
    @ApiOperation("get")
    @ApiImplicitParam(name = "param1", value = "参数1", dataType = "Object", paramType = "query", example = "value1")
    @GetMapping("get/{path}")
    public MyResult<Object> get(@PathVariable String path, @RequestParam(required = false) Date myDate
            , @RequestParam Integer num, @ApiIgnore @RequestParam Map<String, String> mapParam) {
        return MyResult.success(mapParam);
    }
    /**
     * 自定义属性绑定，只对当前Controller有效，
     * 方法必须要有个{@link WebDataBinder}入参，使用它可以：
     *  通过addValidators来自定义参数校验
     *  通过registerCustomEditor注册属性绑定器来自定义属性绑定
     * 每次请求都要绑定一次（说明每次请求WebDataBinder都要创建一次）
     *
     * InitBinder的value属性用于限定要处理的方法参数，如果没有指定则每个参数都会被绑定一次，逻辑在{@link InitBinderDataBinderFactory#isBinderMethodApplicable}
     */
    @InitBinder("myDate")
    public void initBinder1(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @Value("${uploadPath:/opt/IBM/ABC/test/file/}")
    private String uploadPath;

    /**
     * RequestPart是用于获取HTTP请求中的文件内容，即表单中的一个文件项，主要用于文件上传操作
     * @param file
     * @return
     * @throws IOException
     */
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

    /**
     * 重定向的地址不需要做url编码
     * @param resp
     * @throws IOException
     */
    @GetMapping("redirect")
    public void redirect(HttpServletResponse resp) throws IOException {
        String url = "https://baijiahao.baidu.com/s?id=1721805863551776827&wfr=spider&for=pc";
        resp.sendRedirect(url);
    }

}
