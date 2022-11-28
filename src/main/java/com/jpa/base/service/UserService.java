package com.jpa.base.service;

import com.jpa.base.Dao.Entities.Followers;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private FollowerRepository followerRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public void showUserProfile(Principal principal,Model model,Integer id){
        String condition;
        String userName = principal.getName();
        User currentUser = userRepository.getUserByUserName(userName);
        List<Followers> f = followerRepository.isfollow(currentUser.getId(), id);
        if (f.isEmpty()) {
            condition = "True";
        } else {
            condition = "False";
        }
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = userOptional.get();
        model.addAttribute("condition", condition);
        model.addAttribute("user", user);
    }
    public void showAllUsers(Model model){
        List<User> users = this.userRepository.findAll();
        model.addAttribute("users", users);
    }
    public void register(Model model){
        model.addAttribute("user", new User());
    }
    public void registerSuccess(User user){
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
