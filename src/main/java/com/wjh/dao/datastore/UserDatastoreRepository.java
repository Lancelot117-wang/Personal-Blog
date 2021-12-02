package com.wjh.dao.datastore;

import com.wjh.model.datastore.User;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface UserDatastoreRepository extends DatastoreRepository<User, Long> {
}
