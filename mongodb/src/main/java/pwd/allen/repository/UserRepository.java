package pwd.allen.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.DeleteQuery;
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

    // 分页查询
    Page<User> findByAgeGreaterThanEqual(Integer age, Pageable pageable);

    /**
     * 通过@Query自定义查询
     * @param name
     * @return
     */
//    @Query("{ name: /?0/ }")  // 这个也能达到效果，但是idea会有代码错误提示
    @Query("{ name: { $regex: ?0 } }")
    List<User> findByNameLike2(@Param("name") String name);

    // 删除操作

    /**
     * 返回类型需要是long
     * @param name
     * @return
     */
    @DeleteQuery("{ 'name' : ?0 }")
    long deleteUserByName(String name);

    /**
     * TODO 报错
     * @param id
     * @param name
     * @return
     */
    @Query("{$set: {name: ?1 }}")
    UpdateResult updateNameById(String id, String name);

}
