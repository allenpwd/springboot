package pwd.allen.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pwd.allen.filter.MyFilter;
import pwd.allen.interceptor.MyInterceptor;

/**
 * 自定义spring mvc配置，并且保留了springboot默认的配置功能
 * 这种自定义方式，spring.jackson.default-property-inclusion=non_empty有效
 *
 * @author 门那粒沙
 * @create 2021-05-01 9:04
 **/
@Configuration
public class WebConfig2 implements WebMvcConfigurer {

    /**
     * 添加自定义的过滤器
     *
     * 另一种方法是在过滤器类上添加注解WebFilter，然后在启动类上添加ServletComponentScan
     * @see javax.servlet.annotation.WebFilter
     * @see org.springframework.boot.web.servlet.ServletComponentScan
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
     * 添加自定义的拦截器，只处理/my/路径下的
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/my/**");
    }
}
