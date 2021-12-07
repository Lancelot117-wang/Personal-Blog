package com.wjh.dao;

import com.wjh.dto.BlogDTO;
import com.wjh.dto.PageDTO;
import com.wjh.vo.BlogQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogDAO {

    BlogDTO saveBlog(BlogDTO blogDTO);

    BlogDTO findBlogById(Long id);

    BlogDTO getHTMLContent(Long id);

    PageDTO<BlogDTO> listBlog(Pageable pageable, BlogQuery blog);

    PageDTO<BlogDTO> listBlog(Pageable pageable);

    PageDTO<BlogDTO> listBlog(Pageable pageable, Long tagId);

    PageDTO<BlogDTO> listBlog(String query, Pageable pageable);

    List<BlogDTO> listRecommendBlogTop(Integer size);

    Map<String, List<BlogDTO>> archiveBlog();

    Long countBlog();

    BlogDTO updateBlog(Long id, BlogDTO blogDTO);

    void deleteBlog(Long id);
}
