package com.wjh.repository.datastore;

import com.wjh.model.datastore.Type;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeDatastoreRepository extends DatastoreRepository<Type, Long> {

    Type findByName(String name);

    @Query("select * from |com.wjh.model.datastore.Type| limit @size")
    List<Type> findTop(@Param("size") Integer size);
}
