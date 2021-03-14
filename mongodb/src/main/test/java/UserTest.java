import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import pwd.allen.MongoMain;
import pwd.allen.Pager;
import pwd.allen.entity.User;
import pwd.allen.repository.UserRepository;
import pwd.allen.service.UserService;

import java.util.Date;
import java.util.List;

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
        userService.save(user);

        user = userService.getById("666");
        System.out.println(user);

        userRepository.save(user);
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

    @Test
    public void template() {
        Criteria criteria = Criteria.where("age").gt(1).and("birthday").lt(new Date());
        Query query = new Query(criteria);

        long count = mongoTemplate.count(query, User.class);
        System.out.println(count);

        query.with(PageRequest.of(0, 1));
        List<User> list = mongoTemplate.find(query, User.class);
        System.out.println(list);

    }

}
