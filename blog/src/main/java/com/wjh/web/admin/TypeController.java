package com.wjh.web.admin;

import com.wjh.po.Type;
import com.wjh.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction =  Sort.Direction.DESC)
                                    Pageable pageable, Model model) {


       model.addAttribute("page", typeService.listType(pageable));
        return "admin/group";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/group-add";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/group-add";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type0 = typeService.getTypeByName(type.getName());
        if(type0 != null){
            result.rejectValue("name", "nameError", "Existing type can't be added again");
        }
        if(result.hasErrors()) {
            return "admin/group-add";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message", "Operation Failed");
        }
        else {
            attributes.addFlashAttribute("message", "Operation Success");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type0 = typeService.getTypeByName(type.getName());
        if(type0 != null){
            result.rejectValue("name", "nameError", "Existing type can't be added again");
        }
        if(result.hasErrors()) {
            return "admin/group-add";
        }
        Type t = typeService.updateType(id, type);
        if(t == null){
            attributes.addFlashAttribute("message", "Operation Failed");
        }
        else {
            attributes.addFlashAttribute("message", "Operation Success");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "Operation Success");
        return "redirect:/admin/types";
    }
}
