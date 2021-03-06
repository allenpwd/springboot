import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.DbApplication;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

/**
 * @author 门那粒沙
 * @create 2021-06-26 8:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbApplication.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void userSelect() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
