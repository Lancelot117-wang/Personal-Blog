package com.wjh.dao.datastore;

import com.wjh.model.datastore.Type;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface TypeDatastoreRepository extends DatastoreRepository<Type, Long> {
}
