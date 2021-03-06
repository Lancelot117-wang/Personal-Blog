package com.wjh.web.admin;

import com.wjh.po.Tag;
import com.wjh.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction =  Sort.Direction.DESC)
                                    Pageable pageable, Model model) {


       model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tag";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-add";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-add";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag0 = tagService.getTagByName(tag.getName());
        if(tag0 != null){
            result.rejectValue("name", "nameError", "Existing tag can't be added again");
        }
        if(result.hasErrors()) {
            return "admin/tag-add";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message", "Operation Failed");
        }
        else {
            attributes.addFlashAttribute("message", "Operation Success");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Tag tag0 = tagService.getTagByName(tag.getName());
        if(tag0 != null){
            result.rejectValue("name", "nameError", "Existing tag can't be added again");
        }
        if(result.hasErrors()) {
            return "admin/tag-add";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message", "Operation Failed");
        }
        else {
            attributes.addFlashAttribute("message", "Operation Success");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Operation Success");
        return "redirect:/admin/tags";
    }
}
