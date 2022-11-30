package com.jpa.base.service;

import com.jpa.base.Dao.Entities.Followers;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        String userName = getCurrentUserEmail(principal);
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
    public void showCurrentUserProfile(Principal principal, Model model){
        String userName = getCurrentUserEmail(principal);
        User user = userRepository.getUserByUserName(userName);
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
    public String googleCurrentUserName() {

        DefaultOAuth2User userDetails =  (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAttribute("name");
    }
    public String googleCurrentUserEmail() {

        DefaultOAuth2User userDetails =  (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAttribute("email");
    }


    public void isUserAlreadyExists(Principal principal, Model model){
//        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) principal;
        String currentEmail = getCurrentUserEmail(principal);
        List<User> users = userRepository.getUserByEmail(currentEmail);
        if(users.isEmpty()){

            User user = new User();
            user.setEmail(googleCurrentUserEmail());
            user.setRole("ROLE_USER");
            user.setName(googleCurrentUserName());
            user.setPassword(passwordEncoder.encode("12345678"));
            userRepository.save(user);
        }
    }
    public String currentUserName(@AuthenticationPrincipal OAuth2User principal) {

        DefaultOAuth2User userDetails =  (DefaultOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAttribute("login");
    }
    public String getCurrentUserEmail(Principal principal){
        if(principal instanceof OAuth2AuthenticationToken){
            DefaultOidcUser oidcUser = (DefaultOidcUser) (((OAuth2AuthenticationToken) principal).getPrincipal());
            String currentEmail = (String) oidcUser.getAttributes().get("email");
            return currentEmail;
        }
        else{
            String currentEmail = principal.getName();
            return currentEmail;
        }
    }
}
