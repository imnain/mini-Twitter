package com.jpa.base.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/user")
    public ModelAndView screen(){
        ModelAndView mav = new ModelAndView("/user_index");
        return mav;
    }
}
