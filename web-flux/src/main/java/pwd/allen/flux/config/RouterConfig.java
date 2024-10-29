package pwd.allen.flux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 除了传统的基于注解的控制器，SpringWebFlux还支持基于函数式编程的路由配置。
 *
 * @author pwdan
 * @create 2024-10-29 9:36
 **/
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/router/user/{id}"), handler::getUserById)
                .andRoute(RequestPredicates.GET("/router/users"), handler::getAllUsers);
    }
}
