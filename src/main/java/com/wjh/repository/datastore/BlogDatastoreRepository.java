package com.wjh.repository.datastore;

import com.wjh.model.datastore.Blog;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface BlogDatastoreRepository extends DatastoreRepository<Blog, Long> {
}
