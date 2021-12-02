package com.wjh.service;

import com.wjh.model.User;

public interface UserService {

    User checkUser(String username, String password);

}
