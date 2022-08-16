package com.jpa.base.controller;

import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.Tweet;
import com.jpa.base.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

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
    @PostMapping("/register_success")
    public ModelAndView register_success(User user){
        userRepository.save(user);
        ModelAndView mav = new ModelAndView("register_success");
        return mav;
    }
    /*@GetMapping("/main")
    public ModelAndView mainScreen(Model model){
        model.addAttribute("tweet", new Tweet());
        ModelAndView mav = new ModelAndView("main");
        return mav;
    }
    */


}
