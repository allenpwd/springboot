package pwd.allen.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author lenovo
 * @create 2021-03-03 14:42
 **/
public abstract class MongoDbService<T> {
    protected static final Logger logger = LoggerFactory.getLogger(MongoDbService.class);

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 保存对象
     * @param obj
     * @return
     */
    public boolean add(T obj) {
        mongoTemplate.save(obj);
        return true;
    }

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll() {
        return mongoTemplate.findAll(this.getEntityClass());
    }

    /***
     * 根据id查询
     * @param id
     * @return
     */
    public T getById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @return
     */
    public T getByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 更新对象
     *
     * @param id
     * @param update
     * @return
     */
    public boolean updateBook(String id, Update update) {
        Query query = new Query(Criteria.where("_id").is(id));
        // updateFirst 更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query, update, this.getEntityClass());
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query, update, this.getEntityClass());
        return true;
    }

    /***
     * 删除对象
     * @param obj
     * @return
     */
    public boolean deleteBook(T obj) {
        if (obj != null) {
            mongoTemplate.remove(obj);
        }
        return true;
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public boolean deleteBookById(String id) {
        // findOne
        T obj = getById(id);
        // delete
        deleteBook(obj);
        return true;
    }

    /**
     * 模糊查询
     * @param search
     * @return
     */
    public List<T> findByLikes(String search) {
        Query query = new Query();
        Criteria criteria = null;
//        criteria = Criteria.where("name").regex(String.format(".*?%s.*", search));
        Pattern pattern = Pattern.compile("^.*" + search + ".*$" , Pattern.CASE_INSENSITIVE);
        criteria = Criteria.where("name").regex(pattern);
        query.addCriteria(criteria);
        return mongoTemplate.findAllAndRemove(query, this.getEntityClass());
    }

    private Class<T> getEntityClass() {
        return getSuperClassGenricType(this.getClass());
    }

    public static <T> Class<T> getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    public static Class getSuperClassGenricType(Class clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        } else {
            Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
            if (index < params.length && index >= 0) {
                return !(params[index] instanceof Class) ? Object.class : (Class)params[index];
            } else {
                return Object.class;
            }
        }
    }
}
