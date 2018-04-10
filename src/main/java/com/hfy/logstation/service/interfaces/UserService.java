package com.hfy.logstation.service.interfaces;

import com.hfy.logstation.entity.User;

public interface UserService {

    User signUp(User user);
    String signIn(User user);
}
