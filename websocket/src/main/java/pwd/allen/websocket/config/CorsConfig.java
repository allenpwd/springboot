package pwd.allen.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义spring mvc配置，并且保留了springboot默认的配置功能
 * 这种自定义方式，spring.jackson.default-property-inclusion=non_empty有效
 *
 * @author 门那粒沙
 * @create 2021-05-01 9:04
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 添加自定义的拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/log/**");
    }

//    /**
//     * 这种方式不能根据请求头的origin来设置
//     * @return
//     */
//    @Bean
//    public CorsWebFilter corsWebFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        //配置跨域
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsWebFilter(source);
//    }
}
