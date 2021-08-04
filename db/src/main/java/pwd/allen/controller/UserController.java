package pwd.allen.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;

import java.util.List;

/**
 * 用户类
 * @author 门那粒沙
 * @create 2021-04-18 16:07
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据ID查询用户
     * @param id id
     * @return
     */
    @GetMapping("getById")
    public Object getById(@RequestParam("id") String id) {
        User rel = userMapper.selectById(id);
        return rel;
    }

    @GetMapping("getByName")
    public Object getByName(@RequestParam("name") String name) {
        Page<User> page = new Page<>(1, 2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", name);
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        return userPage;
    }

}
