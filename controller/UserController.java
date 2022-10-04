package com.jpa.base.controller;

import com.jpa.base.Dao.FollowerRepository;
import com.jpa.base.Dao.TweetRepository;
import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.Followers;
import com.jpa.base.Entities.Tweet;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.jpa.base.Entities.User;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    public FollowerRepository followerRepository;
    @RequestMapping("/index")   // User Home Page
    public ModelAndView screen(Model model, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        model.addAttribute("user", user);
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
    @GetMapping("/tweet") // User can Tweet
    public ModelAndView tweet(Model model){
        model.addAttribute("tweet", new Tweet());
        //model.addAttribute("user", new User());
        ModelAndView mav = new ModelAndView("/tweet");
        return mav;
    }
    @GetMapping("/{id}") // Show User Detailed Profile
    public ModelAndView addFollowing(@PathVariable("id") Integer id, Model model){
        //model.addAttribute("followers", new Followers());
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.get();
        model.addAttribute("user", user);
        ModelAndView mav = new ModelAndView("/user_detailed_profile");
        return mav;
    }
    @PostMapping("/follow_user/{id}")   //User can follow
    public ModelAndView follow_user(@PathVariable("id") Integer id, Model model, Followers followers, Principal principal, HttpSession session){
        //Optional<User> userOptional = this.userRepository.findById(id);
        followers.setSid(id);
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        int currentUserId = user.getId();
        followers.setFid(currentUserId);
        followerRepository.save(followers);
       // session.setAttribute("message", new Object());


        ModelAndView mav = new ModelAndView("follow_success");
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
    public ModelAndView show_tweets(Model model, Principal principal){
        //String userName = principal.getName();
        //User user = this.userRepository.getUserByUserName(userName);
        //user.getTweet();
        List<Tweet> tweets = this.tweetRepository.findAll();
        //List<Tweet> tweets = this.tweetRepository.getTweetByUser(user.getId());
        //List<Tweet> tweets = user.getTweet();
        model.addAttribute("tweets", tweets);
        System.out.println(tweets);
        ModelAndView mav = new ModelAndView("/show_tweets");
        return mav;
    }
    @GetMapping("/show_my_tweets")
    public ModelAndView showMyTweets(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        List<Tweet> tweets = this.tweetRepository.getTweetByUser(user.getId());

        model.addAttribute("tweets",tweets);
        ModelAndView mav = new ModelAndView("/show_my_tweets");
        return mav;
    }
    @GetMapping("/show_users")
    public ModelAndView show_users(Model model){
        List<User> users = this.userRepository.findAll();
        model.addAttribute("users", users);
        System.out.println(users);
        ModelAndView mav = new ModelAndView("/show_users");
        return mav;
    }
    @GetMapping("/follow")  //Show Following List of current User.
    public ModelAndView follow(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        //List<User> uname = (List<User>) this.userRepository.getUserByUserId(3);
        List<Followers> followers = this.followerRepository.getFollowerByUser(user.getId());
        //List<Followers> followers = this.followerRepository.findAll();
        model.addAttribute("followers", followers);
        System.out.println(followers);
        ModelAndView mav = new ModelAndView("/follow");
        return mav;
    }
    @GetMapping("/my_followers")  //Show Following List of current User.
    public ModelAndView myfollowers(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        //List<User> uname = (List<User>) this.userRepository.getUserByUserId(3);
        List<Followers> followers = this.followerRepository.getFollowersByUser(user.getId());
        //List<Followers> followers = this.followerRepository.findAll();
        model.addAttribute("followers", followers);
        System.out.println(followers);
        ModelAndView mav = new ModelAndView("/my_followers");
        return mav;
    }
    @GetMapping("/search") //In-Progress
    public ModelAndView search(Model model){
        model.addAttribute("user", new User());
        ModelAndView mav = new ModelAndView("/search");
        return mav;
    }

}
