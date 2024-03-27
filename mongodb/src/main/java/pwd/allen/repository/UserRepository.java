package pwd.allen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pwd.allen.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * MongoRepository允许你在接口中声明方法签名，根据方法名称和参数自动映射到对应的查询
 *
 * @author lenovo
 * @create 2021-03-11 11:16
 **/
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByNameLike(String name);

    Optional<User> findFirstByOrderByAgeDesc();

    @Query("{ 'name': ?0 }")
    List<User> findByName(@Param("name") String name);
}
