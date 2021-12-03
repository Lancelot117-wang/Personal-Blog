package com.wjh.service.impl;

import com.wjh.dao.UserDAO;
import com.wjh.dto.UserDTO;
import com.wjh.repository.jpa.UserRepository;
import com.wjh.model.jpa.User;
import com.wjh.service.UserService;
import com.wjh.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO checkUser(String username, String password) {
        UserDTO userDTO = userDAO.findByUsernameAndPassword(username, MD5Utils.code(password));
        return userDTO;
    }
}
