package com.wjh.repository.datastore;

import com.wjh.model.datastore.Type;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface TypeDatastoreRepository extends DatastoreRepository<Type, Long> {
}
