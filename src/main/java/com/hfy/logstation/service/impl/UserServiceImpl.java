package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.User;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.repository.UserRepository;
import com.hfy.logstation.service.interfaces.UserService;
import com.hfy.logstation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(User user) {
        return userRepository.save(user);
    }

    /*
        返回token
     */
    @Override
    public String signIn(User user) {
        User target = userRepository.findOne(user.getId());
        if ( !target.getPassword().equals(user.getPassword())) {
            throw new ServerException(ResponseEnum.WRONG_PASS);
        }
        return JwtUtil.generateToken(user.getId());
    }
}
