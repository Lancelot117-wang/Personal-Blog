package com.wjh.repository.datastore;

import com.wjh.model.datastore.Tag;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface TagDatastoreRepository extends DatastoreRepository<Tag, Long> {
}
