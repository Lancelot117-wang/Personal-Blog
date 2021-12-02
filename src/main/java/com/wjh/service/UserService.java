package com.wjh.service;

import com.wjh.dto.User;

public interface UserService {

    User checkUser(String username, String password);

}
