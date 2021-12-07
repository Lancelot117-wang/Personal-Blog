package com.wjh.service;

import com.wjh.dto.BlogDTO;
import com.wjh.dto.PageDTO;
import com.wjh.vo.BlogQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    BlogDTO getBlog(Long id);

    BlogDTO getAndConvert(Long id);

    PageDTO<BlogDTO> listBlog(Pageable pageable, BlogQuery blog);

    PageDTO<BlogDTO> listBlog(Pageable pageable);

    PageDTO<BlogDTO> listBlog(Pageable pageable, Long tagId);

    PageDTO<BlogDTO> listBlog(String query, Pageable pageable);

    List<BlogDTO> listRecommendBlogTop(Integer size);

    Map<String, List<BlogDTO>> archiveBlog();

    Long countBlog();

    BlogDTO saveBlog(BlogDTO blog);

    BlogDTO updateBlog(Long id, BlogDTO blog);

    void deleteBlog(Long id);
}
