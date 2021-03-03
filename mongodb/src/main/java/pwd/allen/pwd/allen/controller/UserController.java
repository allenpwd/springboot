package pwd.allen.pwd.allen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pwd.allen.entity.User;
import pwd.allen.service.UserService;

import java.util.List;

/**
 * @author lenovo
 * @create 2021-03-03 15:35
 **/
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public Object saveObj(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/findOne")
    public User findOne(@RequestParam String id) {
        return userService.getById(id);
    }

    @GetMapping("/findOneByName")
    public User findOneByName(@RequestParam String name) {
        return userService.getByName(name);
    }
}
