package pwd.allen.service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;
import pwd.allen.Pager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
    public boolean save(T obj) {
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
     * 更新对象
     *
     * @param id
     * @param update
     * @return
     */
    public long update(String id, Update update) {
        Query query = new Query(Criteria.where("_id").is(id));
        // updateFirst 更新查询返回结果集的第一条
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, this.getEntityClass());
        // updateMulti 更新查询返回结果集的全部
        // mongoTemplate.updateMulti(query, update, this.getEntityClass());
        // upsert 更新对象不存在则去添加
        // mongoTemplate.upsert(query, update, this.getEntityClass());
        return updateResult.getModifiedCount();
    }

    /***
     * 删除对象
     * @param obj
     * @return
     */
    public long delete(T obj) {
        long rel = 0;
        if (obj != null) {
            DeleteResult remove = mongoTemplate.remove(obj);
            rel = remove.getDeletedCount();
        }
        return rel;
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public long deleteById(String id) {
        // findOne
        T obj = getById(id);
        // delete
        return delete(obj);
    }

    /**
     * 查询
     * @param paramMap
     * @return
     */
    public List<T> find(Map<String, Object> paramMap) {
        Query query = new Query();
        setParam(query, paramMap);
        return mongoTemplate.find(query, this.getEntityClass());
    }

    private static void setParam(Query query, Map<String, Object> paramMap) {
        if (!CollectionUtils.isEmpty(paramMap)) {
            paramMap.forEach((key, value) -> {
                String type = null;
                if (value instanceof String) {
                    String val = value.toString();
                    if (val.contains(":")) {
                        type = val.substring(0, val.indexOf(":"));
                        val = val.substring(val.indexOf(":") + 1);
                        value = val;
                    }
                    if (val.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        try {
                            value = new SimpleDateFormat("yyyy-MM-dd").parse(val);
                        } catch (ParseException e) {}
                    }
                }
                if ("regex".equals(type)) {
                    query.addCriteria(Criteria.where(key).regex(value.toString()));
                } else if ("lt".equals(type)) {
                    query.addCriteria(Criteria.where(key).lt(value));
                } else if ("gt".equals(type)) {
                    query.addCriteria(Criteria.where(key).gt(value));
                } else if ("lte".equals(type)) {
                    query.addCriteria(Criteria.where(key).lte(value));
                } else if ("gte".equals(type)) {
                    query.addCriteria(Criteria.where(key).gte(value));
                } else {
                    query.addCriteria(Criteria.where(key).is(value));
                }
            });
        }
    }

    /**
     * 分页查询
     * @param pager
     * @return
     */
    public Pager<T> pager(Pager<T> pager) {
        Query query = new Query();
        setParam(query, pager.getParameters());
        pager.setTotal(mongoTemplate.count(query, this.getEntityClass()));
        query.with(PageRequest.of(pager.getPageNumber() - 1, pager.getPageSize(), Sort.by(Sort.Direction.fromString(pager.getDirect()), pager.getProperty())));
        pager.setList(mongoTemplate.find(query, this.getEntityClass()));
        return pager;
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
