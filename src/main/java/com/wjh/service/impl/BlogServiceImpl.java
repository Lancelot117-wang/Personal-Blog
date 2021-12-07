package com.wjh.service.impl;

import com.wjh.dao.BlogDAO;
import com.wjh.dto.BlogDTO;
import com.wjh.dto.PageDTO;
import com.wjh.service.BlogService;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDAO blogDAO;

    @Override
    public BlogDTO getBlog(Long id) {
        return blogDAO.findBlogById(id);
    }

    @Transactional
    @Override
    public BlogDTO getAndConvert(Long id) {
        return blogDAO.getHTMLContent(id);
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDAO.listBlog(pageable, blog);
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable) {
        return blogDAO.listBlog(pageable);
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable, Long tagId) {
        return blogDAO.listBlog(pageable, tagId);
    }

    @Override
    public PageDTO<BlogDTO> listBlog(String query, Pageable pageable) {
        return blogDAO.listBlog(query, pageable);
    }

    @Override
    public List<BlogDTO> listRecommendBlogTop(Integer size) {
        return blogDAO.listRecommendBlogTop(size);
    }

    @Override
    public Map<String, List<BlogDTO>> archiveBlog() {
        return blogDAO.archiveBlog();
    }

    @Override
    public Long countBlog() {
        return blogDAO.countBlog();
    }

    @Transactional
    @Override
    public BlogDTO saveBlog(BlogDTO blogDTO) {
        return blogDAO.saveBlog(blogDTO);
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        return blogDAO.updateBlog(id, blogDTO);
    }

    @Override
    public void deleteBlog(Long id) {
        blogDAO.deleteBlog(id);
    }
}
