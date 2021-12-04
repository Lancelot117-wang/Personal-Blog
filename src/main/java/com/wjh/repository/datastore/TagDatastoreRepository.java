package com.wjh.repository.datastore;

import com.wjh.model.datastore.Tag;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagDatastoreRepository extends DatastoreRepository<Tag, Long> {

    Tag findByName(String name);

    @Query("select * from |com.wjh.model.datastore.Tag| limit @size")
    List<Tag> findTop(@Param("size") Integer size);
}
