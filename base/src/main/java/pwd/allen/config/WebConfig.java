package pwd.allen.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pwd.allen.filter.MyFilter;
import pwd.allen.interceptor.MyInterceptor;

/**
 * @author lenovo
 * @create 2021-03-11 9:07
 **/
@Configuration
//@ServletComponentScan
public class WebConfig extends WebMvcConfigurationSupport {

    /**
     * 添加自定义的过滤器
     *
     * 另一种方法是在过滤器类上添加注解WebFilter，然后在启动类上添加ServletComponentScan
     * @see javax.servlet.annotation.WebFilter
     * @see ServletComponentScan
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean reqResFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        //配置过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //设置过滤器名称
        filterRegistrationBean.setName("myFilter");
        //执行次序
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    /**
     * 添加自定义的拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
