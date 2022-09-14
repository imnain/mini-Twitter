package com.jpa.base.controller;

import com.jpa.base.Dao.TweetRepository;
import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.jpa.base.Entities.User;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @RequestMapping("/index")
    public ModelAndView screen(Model model, Principal principal){
        ModelAndView mav = new ModelAndView("/user_index");
        return mav;
    }
    @RequestMapping("/profile")
    public ModelAndView profile(Model model, Principal principal){
        ModelAndView mav = new ModelAndView("/user_profile");
        return mav;
    }
    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
    }
    @GetMapping("/tweet")
    public ModelAndView tweet(Model model){
        model.addAttribute("tweet", new Tweet());
        //model.addAttribute("user", new User());
        ModelAndView mav = new ModelAndView("/tweet");
        return mav;
    }
    @PostMapping("/tweet_success")
    public ModelAndView tweet_success(@ModelAttribute Tweet tweet, Principal principal){

        //tweetRepository.save(tweet);
        String name = principal.getName();
        User user = this.userRepository.getUserByUserName(name);
        tweet.setUser(user);
        user.getTweet().add(tweet);
        this.userRepository.save(user);
        System.out.println("Data" + tweet);
        ModelAndView mav = new ModelAndView("/tweet_success");
        return mav;
    }
    @GetMapping("/show_tweets")
    public ModelAndView show_tweets(Model model){
        List<Tweet> tweets = this.tweetRepository.findAll();
        model.addAttribute("tweets", tweets);
        ModelAndView mav = new ModelAndView("/show_tweets");
    }
}
