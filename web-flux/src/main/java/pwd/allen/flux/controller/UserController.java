package pwd.allen.flux.controller;

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

    @GetMapping("/list")
    public Flux<User> getAllUsers() {
        return Flux.just(
                new User("1", "User1"),
                new User("2", "User2"),
                new User("3", "User3")
        );
    }
}

