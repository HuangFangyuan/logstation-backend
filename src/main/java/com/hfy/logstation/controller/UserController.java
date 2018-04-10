package com.hfy.logstation.controller;

import com.hfy.logstation.entity.Response;
import com.hfy.logstation.entity.User;
import com.hfy.logstation.service.interfaces.UserService;
import com.hfy.logstation.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Response signUp(@RequestParam("account") String account,
                           @RequestParam("pass") String pass,
                           @RequestParam("name") String name) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(pass);
        user.setName(name);
        userService.signUp(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public Response signIn(@RequestParam("account") String account,
                           @RequestParam("pass") String pass) {
        User user = new User();
        user.setAccount(account);
        user.setPassword(pass);
        return ResponseUtil.success(userService.signIn(user));
    }
}
