package com.wjh.service;

import com.wjh.model.jpa.User;

public interface UserService {

    User checkUser(String username, String password);

}
