package com.jpa.base.service;

import com.jpa.base.Dao.Entities.Followers;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private LikeRepository likeRepository;

    public void unfollowUser(Principal principal, Model model, Integer id){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        User user2 = userRepository.getUserByUserId(id);
        int currentUserId = user.getId();
        model.addAttribute("users", user2);
        followerRepository.isunfollow(currentUserId, id);
    }
    public void followUser(Principal principal, Model model, Integer id, Followers followers){
        String userName = principal.getName();
        User user = userRepository.getUserByUserName(userName);
        User user2 = userRepository.getUserByUserId(id);
        int currentUserId = user.getId();
        model.addAttribute("users", user2);
        followers.setFid(currentUserId);
        followers.setSid(id);
        followerRepository.save(followers);
    }
    public void showUserFollowing(Principal principal, Model model){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        List<Followers> followers = this.followerRepository.getFollowerByUser(user.getId());
        model.addAttribute("followers", followers);
    }
    public void showUserFollowers(Principal principal, Model model){
        String userName = principal.getName();
        User user = this.userRepository.getUserByUserName(userName);
        List<Followers> followers = this.followerRepository.getFollowersByUser(user.getId());
        model.addAttribute("followers", followers);
    }
}
