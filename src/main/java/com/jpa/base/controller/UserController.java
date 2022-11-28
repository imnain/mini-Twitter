package com.jpa.base.controller;

import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import com.jpa.base.Dao.Entities.Followers;
import com.jpa.base.Dao.Entities.Like;
import com.jpa.base.Dao.Entities.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.jpa.base.Dao.Entities.User;
import java.security.Principal;
import com.jpa.base.service.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userservice;
    @Autowired
    private TweetService tweetService;
    @Autowired
    private FollowService followService;
    @RequestMapping("/index")
    public ModelAndView screen(Model model, Principal principal){
        ModelAndView mav = new ModelAndView("/user_index");
        return mav;
    }
    @RequestMapping("/profile")     // show user profile
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
    /* ------------Tweet Controller------------------- */
    @GetMapping("/tweet") // User can Tweet
    public ModelAndView tweet(Model model){
        tweetService.postTweet(model);
        ModelAndView mav = new ModelAndView("/tweet");
        return mav;
    }
    @PostMapping("/like_tweet/{id}")
    public ModelAndView like_user(@PathVariable("id") Integer id, String condition, Model model, Like like, Principal principal){
       tweetService.likeTweet(principal, model, id, like);
        ModelAndView mav = new ModelAndView("detailed_tweet");
        return mav;
    }
    @PostMapping("/unlike_tweet/{id}")
    public ModelAndView unlike_user(@PathVariable("id") Integer id, String condition, Model model, Like like, Principal principal){
        tweetService.unlikeTweet(principal, model, id);
        ModelAndView mav = new ModelAndView("detailed_tweet");
        return mav;
    }
    @PostMapping("/countlike_tweet/{id}")
    public ModelAndView likeCounter(@PathVariable("id") Integer id, Model model){
        tweetService.countLikes(model, id);
        ModelAndView mav = new ModelAndView("like_counter");
        return mav;
    }
    @PostMapping("/tweet_success")
    public ModelAndView tweet_success(@ModelAttribute Tweet tweet, Principal principal){
        tweetService.tweetSuccess(principal, tweet);
        ModelAndView mav = new ModelAndView("/tweet_success");
        return mav;
    }
    @GetMapping("/show")
    public ModelAndView show(Model model){
        tweetService.showAllTweets(model);
        ModelAndView mav = new ModelAndView("/show");
        return mav;
    }
    @GetMapping("/show_my_tweets")
    public ModelAndView showMyTweets(Model model, Principal principal){
        tweetService.showMyTweets(principal, model);
        ModelAndView mav = new ModelAndView("/show_my_tweets");
        return mav;
    }
    /* ------------User Controller------------------- */
    @GetMapping("/{id}") // Show User Detailed Profile
    public ModelAndView addFollowing(@PathVariable("id") Integer id,@RequestParam(required = false) String condition,Principal principal,Model model){
        userservice.showUserProfile(principal,model,id);
        ModelAndView mav = new ModelAndView("/user_detailed_profile");
        return mav;
    }
    @PostMapping("/follow_user/{id}")
    public ModelAndView follow_user(@PathVariable("id") Integer id, Model model, Followers followers, Principal principal){
        followService.followUser(principal, model, id, followers);
        ModelAndView mav = new ModelAndView("follow_success");
        return mav;
    }
    @PostMapping("/unfollow_user/{id}")
    public ModelAndView unfollow_user(@PathVariable("id") Integer id, Model model, Principal principal){
        followService.unfollowUser(principal, model, id);
        ModelAndView mav = new ModelAndView("unfollow_success");
        return mav;
    }
    @GetMapping("/show_users")
    public ModelAndView show_users(Model model){
        userservice.showAllUsers(model);
        ModelAndView mav = new ModelAndView("/show_users");
        return mav;
    }
    @GetMapping("/follow")
    public ModelAndView follow(Model model, Principal principal){
        followService.showUserFollowing(principal, model);
        ModelAndView mav = new ModelAndView("/follow");
        return mav;
    }
    @GetMapping("/my_followers")
    public ModelAndView myfollowers(Model model, Principal principal){
        followService.showUserFollowers(principal, model);
        ModelAndView mav = new ModelAndView("/my_followers");
        return mav;
    }
}
