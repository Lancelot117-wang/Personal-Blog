package com.wjh.service;

import com.wjh.dto.UserDTO;

public interface UserService {

    UserDTO checkUser(String username, String password);

}
