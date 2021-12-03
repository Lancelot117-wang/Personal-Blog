package com.wjh.dao;

import com.wjh.dto.UserDTO;

public interface UserDAO {

    public UserDTO findByUsernameAndPassword(String username, String password);

    public void save(UserDTO userDTO);
}
