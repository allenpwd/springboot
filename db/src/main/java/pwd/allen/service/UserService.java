package pwd.allen.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.util.Date;

/**
 * @author pwdan
 * @create 2023-02-23 9:48
 **/
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    /**
     * 结果：事务失效
     * @param user
     * @return
     */
    public User testTransactional(User user) {
        return addOrUpdate(user);
    }

    @Transactional
    public User addOrUpdate(User user) {
        User userLocal = baseMapper.selectById(user.getId());
        if (userLocal == null) {
            baseMapper.insert(user);
        }
        user.setCreateAt(new Date());
        baseMapper.updateById(user);

        if ("error".equals(user.getUserName())) {
            throw new RuntimeException("出错了");
        }
        return user;
    }


}
