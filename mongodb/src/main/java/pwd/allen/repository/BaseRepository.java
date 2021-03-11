package pwd.allen.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author lenovo
 * @create 2021-03-11 15:45
 **/
@Repository
public class BaseRepository<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;


}
