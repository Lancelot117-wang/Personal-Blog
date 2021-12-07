package com.wjh.repository.datastore;

import com.wjh.model.datastore.Blog;
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.cloud.gcp.data.datastore.repository.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogDatastoreRepository extends DatastoreRepository<Blog, Long> {

    @Query("select * from |com.wjh.model.datastore.Blog| where recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select * from |com.wjh.model.datastore.Blog| limit @size")
    List<Blog> findTop(@Param("size") Integer size);

    @Query("select * from |com.wjh.model.datastore.Blog| where title like @query or content like @query")
    Page<Blog> findByQuery(@Param("query") String query, Pageable pageable);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select * from |com.wjh.model.datastore.Blog| where updateTime starts with @year ")
    List<Blog> findByYear(@Param("year") String year);
}
