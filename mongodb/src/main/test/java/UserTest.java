import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.MongoMain;
import pwd.allen.entity.User;
import pwd.allen.service.UserService;

import java.util.Date;

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
    private UserService userService;

    @Test
    public void user() {
        User user = new User();
        user.setId("666");
        user.setName("junit");
        user.setAge(666);
        user.setBirthday(new Date());

        // 添加，如果已存在id为666的，会覆盖
        userService.add(user);

        user = userService.getById("666");
        System.out.println(user);

    }
}
