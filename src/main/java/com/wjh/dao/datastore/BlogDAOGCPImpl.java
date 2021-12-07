package com.wjh.dao.datastore;

import com.wjh.dao.BlogDAO;
import com.wjh.dto.BlogDTO;
import com.wjh.dto.PageDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.datastore.Blog;
import com.wjh.repository.datastore.BlogDatastoreRepository;
import com.wjh.util.MarkdownUtils;
import com.wjh.util.MyBeanUtils;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "true")
@Repository
public class BlogDAOGCPImpl implements BlogDAO {

    @Autowired
    private BlogDatastoreRepository blogDatastoreRepository;

    @Override
    public BlogDTO saveBlog(BlogDTO blogDTO) {
        if(blogDTO.getId() == null){
            blogDTO.setCreateTime(new Date());
            blogDTO.setUpdateTime(new Date());
            blogDTO.setViews(0);
        } else {
            blogDTO.setUpdateTime(new Date());
        }

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogDTO, blog);
        blogDatastoreRepository.save(blog);

        return blogDTO;
    }

    @Override
    public BlogDTO findBlogById(Long id) {
        Blog blog = blogDatastoreRepository.findById(id).get();
        BlogDTO blogDTO = new BlogDTO();
        BeanUtils.copyProperties(blog, blogDTO);
        return blogDTO;
    }

    @Override
    public BlogDTO getHTMLContent(Long id) {
        Blog blog;
        try {
            blog = blogDatastoreRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new NotFoundException("Blog doesn't exist");
        }
        BlogDTO b = new BlogDTO();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blog.setViews(blog.getViews() + 1);
        blogDatastoreRepository.save(blog);

        return b;
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable, BlogQuery blog) {
        return null;
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable) {
        Page<Blog> blogPage = blogDatastoreRepository.findAll(pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList  = new ArrayList<>();
        blogList.forEach(blog -> {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blog, blogDTO);
            blogDTOList.add(blogDTO);
        });
        PageDTO<BlogDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(blogDTOList);
        pageDTO.setTotalPages(blogPage.getTotalPages());
        pageDTO.setNumber(blogPage.getNumber());
        pageDTO.setFirst(blogPage.isFirst());
        pageDTO.setLast(blogPage.isLast());
        return pageDTO;
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable, Long tagId) {
        return null;
    }

    @Override
    public PageDTO<BlogDTO> listBlog(String query, Pageable pageable) {
        Page<Blog> blogPage = blogDatastoreRepository.findByQuery(query, pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList  = new ArrayList<>();
        blogList.forEach(blog -> {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blog, blogDTO);
            blogDTOList.add(blogDTO);
        });
        PageDTO<BlogDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(blogDTOList);
        pageDTO.setTotalPages(blogPage.getTotalPages());
        pageDTO.setNumber(blogPage.getNumber());
        pageDTO.setFirst(blogPage.isFirst());
        pageDTO.setLast(blogPage.isLast());
        return pageDTO;
    }

    @Override
    public List<BlogDTO> listRecommendBlogTop(Integer size) {
        List<Blog> blogList = blogDatastoreRepository.findTop(size);
        List<BlogDTO> blogDTOList = new ArrayList<>();
        blogList.forEach(blog -> {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blog, blogDTO);
            blogDTOList.add(blogDTO);
        });
        return blogDTOList;
    }

    @Override
    public Map<String, List<BlogDTO>> archiveBlog() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> years = new ArrayList<>();
        years.add(String.valueOf(thisYear - 3));
        years.add(String.valueOf(thisYear - 2));
        years.add(String.valueOf(thisYear - 1));
        years.add(String.valueOf(thisYear));
        Map<String, List<BlogDTO>> map = new HashMap<>();
        for(String year: years) {
            List<BlogDTO> blogDTOList = blogDatastoreRepository.findByYear(year).stream().map(
                    blog->{
                        BlogDTO blogDTO = new BlogDTO();
                        BeanUtils.copyProperties(blog, blogDTO);
                        return blogDTO;
                    }).collect(Collectors.toList());
            map.put(year, blogDTOList);
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDatastoreRepository.count();
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        Blog b;
        try {
            b = blogDatastoreRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new NotFoundException("Blog doesn't exist");
        }
        BeanUtils.copyProperties(blogDTO, b, MyBeanUtils.getNullPropertyNames(blogDTO));
        b.setUpdateTime(new Date());
        blogDatastoreRepository.save(b);
        BlogDTO returnBlog = new BlogDTO();
        BeanUtils.copyProperties(b, returnBlog);
        return returnBlog;
    }

    @Override
    public void deleteBlog(Long id) {
        blogDatastoreRepository.deleteById(id);
    }
}
