package com.wjh.repository.datastore;

import com.wjh.model.datastore.Comment;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;

public interface CommentDatastoreRepository extends DatastoreRepository<Comment, Long> {
}
