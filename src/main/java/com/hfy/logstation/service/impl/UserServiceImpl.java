package com.hfy.logstation.service.impl;

import com.hfy.logstation.entity.User;
import com.hfy.logstation.exception.ResponseEnum;
import com.hfy.logstation.exception.ServerException;
import com.hfy.logstation.repository.UserRepository;
import com.hfy.logstation.service.interfaces.UserService;
import com.hfy.logstation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

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
    public String signIn(@NotNull User user) {
        Optional.ofNullable(getUser(user.getId()))
                .ifPresent(u -> {
                    if (!u.getPassword().equals(user.getPassword())) {
                        throw new ServerException(ResponseEnum.WRONG_PASS);
                    }
                });
        return JwtUtil.generateToken(user.getId());
    }

    @Override
    public User getUser(int id) {
        return Optional.ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new ServerException("not exist this id:" + id));
    }
}
