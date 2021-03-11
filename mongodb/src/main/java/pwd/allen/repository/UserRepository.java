package pwd.allen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pwd.allen.entity.User;

/**
 * @author lenovo
 * @create 2021-03-11 11:16
 **/
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
