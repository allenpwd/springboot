import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.EstimatedDocumentCountOptions;
import com.mongodb.internal.client.model.CountOptionsHelper;
import org.assertj.core.util.Lists;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import pwd.allen.MongoMain;
import pwd.allen.Pager;
import pwd.allen.entity.User;
import pwd.allen.repository.UserRepository;
import pwd.allen.service.UserService;

import javax.websocket.RemoteEndpoint;
import java.util.*;

/**
 * @author lenovo
 * @create 2021-03-03 16:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoMain.class)
public class UserTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save() {
        User user = new User();
        user.setId("666");
        user.setName("junit");
        user.setAge(666);
        user.setBirthday(new Date());

        // 添加，如果已存在id为666的，会覆盖
        System.out.println(userService.save(user));

        user = userService.getById("666");
        System.out.println(user);

        userRepository.save(user);
    }

    @Test
    public void remove() {
        User user = new User();
        user.setId("666");
        user.setName("junit");

        System.out.println(userService.delete(user));
    }

    @Test
    public void page() {
        Pager<User> userPager = new Pager<>();
        userPager.getParameters().put("name", "regex:.*e.*");
        userPager.getParameters().put("birthday", "lt:2021-03-11");
        userPager.setDirect("desc");
        userPager.setProperty("birthday");
        userPager.setPageSize(4);
        Pager<User> pager = userService.pager(userPager);
        System.out.println(pager);
    }

    /**
     * 根据name模糊搜索
     */
    @Test
    public void repo() {
        User user = new User();
        user.setName(".*te.*");
//        user.setBirthday(new Date());

        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.regex());

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "birthday"));

        Page<User> userPage = userRepository.findAll(Example.of(user, exampleMatcher), pageRequest);

        System.out.println(userPage);
    }

    /**
     * age>=1 and birthday<now
     *
     * Query: { "age" : { "$gte" : 1}, "birthday" : { "$lt" : { "$date" : 1616920424403}}}, Fields: {}, Sort: {}
     */
    @Test
    public void tempCond() {
        //<editor-fold desc="方式一">
//        Criteria criteria = Criteria.where("age").gte(1).and("birthday").lt(new Date());
//        Query query = new Query(criteria);
//
//        long count = mongoTemplate.count(query, User.class);
//        System.out.println(count);
//
//        query.with(PageRequest.of(0, 1));
//        List<User> list = mongoTemplate.find(query, User.class);
//        System.out.println(list);
        //</editor-fold>


        //<editor-fold desc="方式二">
        BasicDBObject queryObj = BasicDBObject.parse("{\n" +
                "    \"age\": {\n" +
                "        \"$gte\": 1\n" +
                "    },\n" +
                "    \"birthday\": {\n" +
                "        \"$lt\": {\n" +
                "            \"$date\": 1616920424403\n" +
                "        }\n" +
                "    }\n" +
                "}");
        BasicDBObject sortObj = BasicDBObject.parse("{\n" +
                "    \"birthday\": -1\n" +
                "}");
        MongoCollection<Document> collection = mongoTemplate.getCollection("user");
        long count = collection.countDocuments(queryObj);
        System.out.println(count);
        FindIterable<Map> result = collection.find(queryObj, Map.class);
        result.sort(sortObj);
        result.skip(0).limit(2);
        ArrayList<Map> list = Lists.newArrayList(result);
        System.out.println(list);
        //</editor-fold>
    }

    /**
     * 使用分组统计
     */
    @Test
    public void aggregate() {
        // 按月分组统计人数和最大年龄和每组不同的名称数，并按人数降序，并跳过第一条记录
        String strJSON = FileUtil.readUtf8String("按月分组降序.json");

        MongoCollection<Document> collection = mongoTemplate.getCollection("user");

        List<Map> stages = JSONUtil.parseArray(strJSON).toList(Map.class);

        ArrayList<BasicDBObject> bsonList = new ArrayList<>();
        if (stages != null) {
            for (Map stage : stages) {
                bsonList.add(new BasicDBObject(stage));
            }
        }
        AggregateIterable<Map> iterable = collection.aggregate(bsonList, Map.class);
        iterable.allowDiskUse(true);
        List<Map> list = IterUtil.toList(iterable);
        System.out.println(list);
    }

}
