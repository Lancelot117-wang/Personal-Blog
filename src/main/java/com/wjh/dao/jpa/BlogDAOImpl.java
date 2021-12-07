package com.wjh.dao.jpa;

import com.wjh.dao.BlogDAO;
import com.wjh.dto.BlogDTO;
import com.wjh.dto.PageDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.jpa.Blog;
import com.wjh.model.jpa.Type;
import com.wjh.repository.jpa.BlogRepository;
import com.wjh.util.MarkdownUtils;
import com.wjh.util.MyBeanUtils;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "false")
@Repository
public class BlogDAOImpl implements BlogDAO {

    @Autowired
    private BlogRepository blogRepository;

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
        blogRepository.save(blog);

        return blogDTO;
    }

    @Override
    public BlogDTO findBlogById(Long id) {
        Blog blog = blogRepository.findById(id).get();
        BlogDTO blogDTO = new BlogDTO();
        BeanUtils.copyProperties(blog, blogDTO);
        return blogDTO;
    }

    @Override
    public BlogDTO getHTMLContent(Long id) {
        Blog blog;
        try {
            blog = blogRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new NotFoundException("Blog doesn't exist");
        }
        BlogDTO b = new BlogDTO();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        blogRepository.updateViews(id);

        return b;
    }

    @Override
    public PageDTO<BlogDTO> listBlog(Pageable pageable, BlogQuery blog) {
        Page<Blog> blogPage = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()));
                }
                if(blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
        List<Blog> blogList = blogPage.getContent();
        List<BlogDTO> blogDTOList  = new ArrayList<>();
        blogList.forEach(blogLocal -> {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtils.copyProperties(blogLocal, blogDTO);
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
    public PageDTO<BlogDTO> listBlog(Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findAll(pageable);
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
        Page<Blog> blogPage = blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        }, pageable);
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
    public PageDTO<BlogDTO> listBlog(String query, Pageable pageable) {
        Page<Blog> blogPage = blogRepository.findByQuery(query, pageable);
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
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        List<Blog> blogList = blogRepository.findTop(pageable);
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
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<BlogDTO>> map = new HashMap<>();
        for(String year: years) {
            List<BlogDTO> blogDTOList = blogRepository.findByYear(year).stream().map(
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
        return blogRepository.count();
    }

    @Override
    public BlogDTO updateBlog(Long id, BlogDTO blogDTO) {
        Blog b;
        try {
            b = blogRepository.findById(id).get();
        } catch (NoSuchElementException e){
            throw new NotFoundException("Blog doesn't exist");
        }
        BeanUtils.copyProperties(blogDTO, b, MyBeanUtils.getNullPropertyNames(blogDTO));
        b.setUpdateTime(new Date());
        blogRepository.save(b);
        BlogDTO returnBlog = new BlogDTO();
        BeanUtils.copyProperties(b, returnBlog);
        return returnBlog;
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
