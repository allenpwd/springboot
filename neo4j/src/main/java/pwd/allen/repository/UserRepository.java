package pwd.allen.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import pwd.allen.entity.User;

import java.util.List;

/**
 * @author pwdan
 * @create 2024-03-28 17:07
 **/
public interface UserRepository extends Neo4jRepository<User, String> {

    // 自定义查询方法也可以在这里声明
    List<User> findByNameLike(String name);
}
