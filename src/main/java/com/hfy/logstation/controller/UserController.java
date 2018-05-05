package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.entity.User;
import com.hfy.logstation.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.hfy.logstation.util.ResponseUtil.success;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Response signUp(@RequestParam("account") String account,
                           @RequestParam("pass") String pass,
                           @RequestParam("name") String name) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(pass);
        user.setName(name);
        userService.signUp(user);
        return success();
    }

    @PostMapping("/signin")
    public Response signIn(@RequestParam("account") String account,
                           @RequestParam("pass") String pass) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(pass);
        return success(userService.signIn(user));
    }
}
