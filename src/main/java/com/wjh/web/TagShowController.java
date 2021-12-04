package com.wjh.web;

import com.wjh.dto.TagDTO;
import com.wjh.service.BlogService;
import com.wjh.service.TagService;
import com.wjh.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, @PathVariable Long id) {
        List<TagDTO> tags = tagService.listTagTop(100);
        if(id == -1) {
            id = tags.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(pageable, id));
        model.addAttribute("activeTagId", id);
        return "tag";
    }
}
