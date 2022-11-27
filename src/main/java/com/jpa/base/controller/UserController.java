package com.jpa.base.controller;

import com.jpa.base.Dao.FollowerRepository;
import com.jpa.base.Dao.LikeRepository;
import com.jpa.base.Dao.TweetRepository;
import com.jpa.base.Dao.UserRepository;
import com.jpa.base.Entities.Followers;
import com.jpa.base.Entities.Like;
import com.jpa.base.Entities.Tweet;
import com.jpa.base.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.jpa.base.Entities.User;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Condition;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    public FollowerRepository followerRepository;
    @Autowired
    public LikeRepository likeRepository;
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
        ModelAndView mav = new ModelAndView("/tweet");
        return mav;
    }
    @GetMapping("/{id}") // Show User Detailed Profile
    public ModelAndView addFollowing(@PathVariable("id") Integer id,@RequestParam(required = false) String condition,Principal principal,Model model){
        String userName = principal.getName();
        User currentUser = userRepository.getUserByUserName(userName);
        List<Followers> f = followerRepository.isfollow(currentUser.getId(), id);
        if(f.isEmpty()){
            condition = "True";
        }
        else{
            condition = "False";
        }
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.get();
        model.addAttribute("condition", condition);
        model.addAttribute("user", user);
        ModelAndView mav = new ModelAndView("/user_detailed_profile");
        return mav;
    }
    @GetMapping("/my/{id}") // In progress
    public ModelAndView show_my_following(@PathVariable("id") Integer id, Model model){
        Optional<User> userOptional = this.userRepository.findById(id);
        User users = userOptional.get();
        model.addAttribute("users", users);
        ModelAndView mav = new ModelAndView("/user_following");
        return mav;
    }
    @PostMapping("/like_tweet/{id}") //user can like tweet
    public ModelAndView like_user(@PathVariable("id") Integer id, String condition, Model model, Like like, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        int currrentUserId = user.getId();
        List<Like> alreadyLiked = likeRepository.isliked(id,currrentUserId);

        if(alreadyLiked.isEmpty()){

            condition = "True";
            like.setUid(currrentUserId);
            like.setTid(id);
            likeRepository.save(like);
            List<OrderResponse> tweets = userRepository.getLike(id);
            model.addAttribute("tweets", tweets);
            model.addAttribute("condition", condition);
            ModelAndView mav = new ModelAndView("detailed_tweet");
            return mav;
        }
        else{
            condition = "False";
            List<OrderResponse> tweets = userRepository.getLike(id);
            model.addAttribute("tweets", tweets);
            model.addAttribute("condition",condition);
            ModelAndView mav = new ModelAndView("detailed_tweet");
            return mav;
        }

    }
    @PostMapping("/unlike_tweet/{id}") //user can unlike tweet
    public ModelAndView unlike_user(@PathVariable("id") Integer id, String condition, Model model, Like like, Principal principal){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        int currrentUserId = user.getId();
          likeRepository.isunliked(id,currrentUserId);
        List<Like> alreadyLiked = likeRepository.isliked(id,currrentUserId);
        if(alreadyLiked.isEmpty()){
            condition = "True";
            List<OrderResponse> tweets = userRepository.getLike(id);
            model.addAttribute("tweets", tweets);
            model.addAttribute("condition", condition);
            ModelAndView mav = new ModelAndView("detailed_tweet");
            return mav;
        }
        else{
            condition = "False";
            List<OrderResponse> tweets = userRepository.getLike(id);
            model.addAttribute("tweets", tweets);
            model.addAttribute("condition",condition);
            ModelAndView mav = new ModelAndView("detailed_tweet");
            return mav;
        }
    }
    @PostMapping("/countlike_tweet/{id}")
    public ModelAndView likeCounter(@PathVariable("id") Integer id, Model model){
        List<Like> list = likeRepository.likeCounter(id);
        int counter = list.size();
        model.addAttribute("counter", counter);
        System.out.println("Counter is " + counter);
        ModelAndView mav = new ModelAndView("like_counter");
        return mav;
    }
    @PostMapping("/follow_user/{id}")   //User can follow
    public ModelAndView follow_user(@PathVariable("id") Integer id, Model model, Followers followers, Principal principal, HttpSession session){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        User user2 = userRepository.getUserByUserId(id);
        int currentUserId = user.getId();
        model.addAttribute("users", user2);
        List<Followers> alreadyFollowed = followerRepository.isfollow(currentUserId, id);
        if(alreadyFollowed.isEmpty()){
            followers.setFid(currentUserId);
            followers.setSid(id);
            followerRepository.save(followers);
            ModelAndView mav = new ModelAndView("follow_success");
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView("alreadyFollowedError");
            return mav;
        }

    }
    @PostMapping("/unfollow_user/{id}")   //User can unfollow
    public ModelAndView unfollow_user(@PathVariable("id") Integer id, Model model, Followers followers, Principal principal, HttpSession session){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        User user2 = userRepository.getUserByUserId(id);
        int currentUserId = user.getId();
        model.addAttribute("users", user2);
        followerRepository.isunfollow(currentUserId, id);
        ModelAndView mav = new ModelAndView("unfollow_success");
        return mav;

    }
    @PostMapping("/tweet_success")
    public ModelAndView tweet_success(@ModelAttribute Tweet tweet, Principal principal){
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
        List<Tweet> tweets = this.tweetRepository.findAll();
        model.addAttribute("tweets", tweets);
        System.out.println(tweets);
        ModelAndView mav = new ModelAndView("/show_tweets");
        return mav;
    }
    @GetMapping("/show")
    public ModelAndView show(Model model){
        List<OrderResponse> tweets = this.userRepository.getJoinInformation();
        model.addAttribute("tweets", tweets);
        System.out.println(tweets);
        ModelAndView mav = new ModelAndView("/show");
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
    public ModelAndView show_users(Integer id, @RequestParam(required = false) String condition, Principal principal, Model model){
        List<User> users = this.userRepository.findAll();
        model.addAttribute("users", users);
        ModelAndView mav = new ModelAndView("/show_users");
        return mav;
    }
    @GetMapping("/follow")  //Show Following List of current User.
    public ModelAndView follow(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        List<Followers> followers = this.followerRepository.getFollowerByUser(user.getId());
        model.addAttribute("followers", followers);
        ModelAndView mav = new ModelAndView("/follow");
        return mav;
    }
    @GetMapping("/my_followers")  //Show Followers List of current User.
    public ModelAndView myfollowers(Model model, Principal principal){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        List<Followers> followers = this.followerRepository.getFollowersByUser(user.getId());
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
