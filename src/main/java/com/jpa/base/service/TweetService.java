package com.jpa.base.service;

import com.jpa.base.Dao.Entities.Like;
import com.jpa.base.Dao.Entities.Tweet;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import com.jpa.base.Dao.Entities.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Service
public class TweetService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserService userService;
    public void likeTweet(Principal principal, Model model, Integer id, Like like){
        String condition;
        String userName = userService.getCurrentUserEmail(principal);
        User user = userRepository.getUserByUserName(userName);
        int currrentUserId = user.getId();
        List<Like> alreadyLiked = likeRepository.isliked(id,currrentUserId);
        if(alreadyLiked.isEmpty()){
            condition = "True";
            like.setUid(currrentUserId);
            like.setTid(id);
            likeRepository.save(like);
        }
        else{
            condition = "False";
        }
        List<OrderResponse> tweets = userRepository.getLike(id);
        model.addAttribute("tweets", tweets);
        model.addAttribute("condition", condition);
    }
    public void unlikeTweet(Principal principal, Model model, Integer id){
        String condition;
        String userName = userService.getCurrentUserEmail(principal);
        User user = userRepository.getUserByUserName(userName);
        int currrentUserId = user.getId();
        likeRepository.isunliked(id,currrentUserId);
        List<Like> alreadyLiked = likeRepository.isliked(id,currrentUserId);
        if(alreadyLiked.isEmpty()){
            condition = "True";
        }
        else{
            condition = "False";
        }
        List<OrderResponse> tweets = userRepository.getLike(id);
        model.addAttribute("tweets", tweets);
        model.addAttribute("condition", condition);
    }
    public void countLikes(Model model, Integer id){
        List<Like> list = likeRepository.likeCounter(id);
        int counter = list.size();
        model.addAttribute("counter", counter);
    }
    public void tweetSuccess(Principal principal, Tweet tweet){
        String userName =userService.getCurrentUserEmail(principal);
        User user = this.userRepository.getUserByUserName(userName);
        tweet.setUser(user);
        user.getTweet().add(tweet);
        this.userRepository.save(user);
    }
    public void showAllTweets(Integer page, Model model){
        Pageable pageable = PageRequest.of(page,5);
        Page<OrderResponse> tweets = this.userRepository.getJoinInformation(pageable);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tweets.getTotalPages());
        model.addAttribute("tweets", tweets);
    }
    public void showMyTweets(Principal principal, Model model){
        String userName = userService.getCurrentUserEmail(principal);
        User user = this.userRepository.getUserByUserName(userName);
        List<Tweet> tweets = this.tweetRepository.getTweetByUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("tweets",tweets);
    }
    public void postTweet(Model model){
        model.addAttribute("tweet", new Tweet());
    }
}
