package pwd.allen.flux.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import org.springframework.web.reactive.function.server.ServerResponse;
import pwd.allen.flux.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author pwdan
 * @create 2024-10-29 9:36
 **/
@Component
public class UserHandler {

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().body(Mono.just(new User(id, "User" + id)), User.class);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ok().body(Flux.just(
                new User("1", "User1"),
                new User("2", "User2"),
                new User("3", "User3")
        ), User.class);
    }
}

