package com.wjh.service.impl;

import com.wjh.dao.datastore.UserDatastoreRepository;
import com.wjh.model.datastore.User;
import com.wjh.service.UserService;
import com.wjh.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "true")
@Service
public class UserServiceGCPImpl implements UserService {

    @Autowired
    private UserDatastoreRepository userDatastoreRepository;

    @Override
    public com.wjh.dto.User checkUser(String username, String password) {
        User user = userDatastoreRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        com.wjh.dto.User userDTO = new com.wjh.dto.User();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
