package com.wjh.dao.datastore;

import com.wjh.dao.UserDAO;
import com.wjh.dto.UserDTO;
import com.wjh.model.datastore.User;
import com.wjh.repository.datastore.UserDatastoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "true")
@Repository
public class UserDAOGCPImpl implements UserDAO {

    @Autowired
    private UserDatastoreRepository userDatastoreRepository;

    @Override
    public UserDTO findByUsernameAndPassword(String username, String password) {
        User user = userDatastoreRepository.findByUsernameAndPassword(username, password);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public void save(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userDatastoreRepository.save(user);
    }
}
