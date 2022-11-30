package com.jpa.base.controller;

import com.jpa.base.Dao.Repository.UserRepository;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public ModelAndView loginView(){
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }
    @GetMapping("/register")
    public ModelAndView register(Model model){
        userService.register(model);
        ModelAndView mav = new ModelAndView("/Register");
        return mav;
    }
    @GetMapping("/about")
    public String about(){
        return "This is the about page.";
    }
    @PostMapping("/register_success")
    public ModelAndView register_success(User user) {
        userService.registerSuccess(user);
        ModelAndView mav = new ModelAndView("register_success");
        return mav;
    }
}
