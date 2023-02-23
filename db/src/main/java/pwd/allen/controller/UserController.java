package pwd.allen.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwd.allen.entity.User;
import pwd.allen.mapper.UserMapper;
import pwd.allen.service.UserService;

import java.util.List;

/**
 * 用户类
 * @author 门那粒沙
 * @create 2021-04-18 16:07
 **/
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("user")
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据ID查询用户
     * @param id id
     * @return
     */
    @GetMapping("getById")
    @ApiOperation(value = "根据ID查询用户", notes = "根据ID查询用户")
    public Object getById(@RequestParam("id") String id) {
        User rel = userService.getById(id);
        return rel;
    }

    @GetMapping("getByName")
    public Object getByName(@RequestParam("name") String name) {
        Page<User> page = new Page<>(1, 2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", name);
        Page<User> userPage = userService.page(page, queryWrapper);
        return userPage;
    }


    @ApiOperation(value = "测试事务", notes = "测试事务")
    @PostMapping("testTransactional")
    @ApiOperationSupport(ignoreParameters = {"createAt"})  // 忽略参数，支持级联属性
    public Object testTransactional(User user) {
        if (user.getId() == null) {
            user.setId(RandomUtil.randomInt(10, 100000));
        }
        userService.testTransactional(user);
        return user;
    }

}
