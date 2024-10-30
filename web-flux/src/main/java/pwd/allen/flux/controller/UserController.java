package pwd.allen.flux.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.flux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("user")
@RestController
public class UserController {

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return Mono.just(new User(id, "User" + id));
    }

    /**
     * 允许返回非Mono、非Flux类型
     * 但这样就是阻塞代码了
     * @param id
     * @return
     */
    @GetMapping("/block/{id}")
    public User getUserById2(@PathVariable String id) {
        return new User(id, "User" + id);
    }

    /**
     * spring mvc 是 HttpServletRequest
     * spring web flux 是 {@link ServerHttpRequest}
     * response也是类似的变化。
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public Flux<User> getAllUsers(ServerHttpRequest request, ServerHttpResponse response) {
        return Flux.just(
                new User("1", "User1"),
                new User("2", "User2"),
                new User("3", "User3")
        );
    }
}

