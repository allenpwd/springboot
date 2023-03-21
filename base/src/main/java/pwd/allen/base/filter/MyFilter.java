package pwd.allen.base.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 自定义过滤器
 *
 * 在过滤器类上添加注解WebFilter，然后在启动类上添加ServletComponentScan
 * @see javax.servlet.annotation.WebFilter
 * @see org.springframework.boot.web.servlet.ServletComponentScan
 *
 */
//@WebFilter(urlPatterns = "/*", filterName = "myFilter")
@Log4j2
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("【MyFilter.doFilter】before");
        chain.doFilter(request,response);
        log.info("【MyFilter.doFilter】after");
    }

    @Override
    public void destroy() {}
}
