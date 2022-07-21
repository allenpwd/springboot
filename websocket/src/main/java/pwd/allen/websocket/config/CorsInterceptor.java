package pwd.allen.websocket.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 门那粒沙
 * @create 2020-02-11 17:54
 **/
@Log4j2
public class CorsInterceptor implements HandlerInterceptor {

    /**
     * 在处理方法之前执行，一般用来做一些准备工作：比如日志，权限检查
     * 如果返回false 表示被拦截，将不会执行处理方法
     * 返回true继续执行处理方法
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String origin = req.getHeader("Origin");
        if (StrUtil.isEmpty(origin)) {
            origin = "*";
        }
        resp.setHeader("Access-Control-Allow-Origin", origin);
        // 如果请求头有特殊的属性，需要在这里设置下，否则浏览器只会发起options请求（猜想：浏览器会根据options请求的响应头信息判断是否允许跨域）
        resp.setHeader("Access-Control-Allow-Headers", "name");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        // 如果响应头有特殊的属性，需要在这里设置下，否则ajax获取不到指定的响应头，虽然在请求信息里能看到
        resp.setHeader("Access-Control-Expose-Headers", "token");
        resp.setHeader("token", "abc");
        return true;
    }
}
