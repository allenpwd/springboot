import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import pwd.allen.flux.controller.UserController;
import pwd.allen.flux.entity.User;

/**
 * TODO 跑不起来
 *
 * @author pwdan
 * @create 2024-10-29 10:51
 **/
@WebFluxTest(controllers = UserController.class)
public class UserHandlerTest {

    /**
     * 由于Webflux是异步的，我们不能直接使用MockMvc客户端访问它，这个时候需要换另一个客户端WebTestClient
     */
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testGetUser() {
        webTestClient.get().uri("/user/get/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("John Doe");
    }

    @Test
    void testGetAllUsers() {
        webTestClient.get().uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).hasSize(3);
    }
}
