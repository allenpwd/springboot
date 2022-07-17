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
        resp.setHeader("Access-Control-Expose-Headers", "*");
        resp.setHeader("token", "abc");
        return true;
    }
}
