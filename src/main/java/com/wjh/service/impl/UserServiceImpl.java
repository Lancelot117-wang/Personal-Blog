package com.wjh.service.impl;

import com.wjh.repository.jpa.UserRepository;
import com.wjh.model.jpa.User;
import com.wjh.service.UserService;
import com.wjh.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "false")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public com.wjh.dto.User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        com.wjh.dto.User userDTO = new com.wjh.dto.User();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
