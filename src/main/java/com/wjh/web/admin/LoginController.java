package com.wjh.web.admin;

import com.wjh.dao.jpa.UserRepository;
import com.wjh.model.jpa.User;
import com.wjh.service.UserService;
import com.wjh.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(username, password);
        if(user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }
        else {
            attributes.addFlashAttribute("message", "Username or password is wrong");
            return "redirect:/admin";
        }
    }

    @GetMapping("/signout")
    public String Signout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @PostMapping("/register")
    public void register(){
        User newUser = new User();
        newUser.setUsername("junhanwang");
        newUser.setPassword(MD5Utils.code("125690"));
        userRepository.save(newUser);
    }
}
