package pwd.allen.base.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pwd.allen.base.filter.MyFilter;
import pwd.allen.base.interceptor.MyInterceptor;

import java.util.List;

/**
 * 自定义spring mvc配置
 * 这种自定义方式，spring.jackson.default-property-inclusion=non_empty无效，knife4j也会失效
 * 因为这种方式接管了webmvc配置，springboot的webmvc自动装配会失效
 * @see org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
 *
 * @author lenovo
 * @create 2021-03-11 9:07
 **/
//@Configuration
//@ServletComponentScan
@Deprecated
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
