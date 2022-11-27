package com.jpa.base.controller;

import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class HomeController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user = new User();
        user.setName("Rohit");
        user.setEmail("rohit@sharma.com");
        user.setBio("cricket");
        user.setPassword("cricket");
        userRepository.save(user);
        return "working";
    }
    @GetMapping("/home")
    public ModelAndView loginView(){
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }
    @GetMapping("/register")
    public ModelAndView register(Model model){
        model.addAttribute("user", new User());
        ModelAndView mav = new ModelAndView("/Register");
        return mav;
    }
    @GetMapping("/about")
    public String about(){
        return "This is the about page.";
    }
    @PostMapping("/register_success")
    public ModelAndView register_success(User user) throws SQLIntegrityConstraintViolationException {
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        ModelAndView mav = new ModelAndView("register_success");
        return mav;
    }
}
