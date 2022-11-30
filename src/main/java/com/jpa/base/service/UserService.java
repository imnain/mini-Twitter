package com.jpa.base.service;

import com.jpa.base.Dao.Entities.Followers;
import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Repository.FollowerRepository;
import com.jpa.base.Dao.Repository.LikeRepository;
import com.jpa.base.Dao.Repository.TweetRepository;
import com.jpa.base.Dao.Repository.UserRepository;
import com.jpa.base.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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
    static Logger log = LogManager.getLogger(UserService.class);
    Response response = new Response();
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
    public void showAllUsers(Model model, Principal principal){
        List<User> users = this.userRepository.findAll();
        String userName = getCurrentUserEmail(principal);
        User user = userRepository.getUserByUserName(userName);
        users.remove(user);
        model.addAttribute("users", users);
    }
    public void register(Model model){
        model.addAttribute("user", new User());
    }
    public Response registerSuccess(User user){
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.save(user);
        }
        catch (DataIntegrityViolationException e){
            log.info("Email already exists in the database, please use a different email", e);
            response.success = false;
            response.message = "Email already exists in the database, please use a different email";
            return response;
        }
        response.success = true;
        return response;
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
    public String getCurrentUserEmail(Principal principal){
        String currentEmail = "";
        try{
            if(principal instanceof OAuth2AuthenticationToken){
                DefaultOidcUser oidcUser = (DefaultOidcUser) (((OAuth2AuthenticationToken) principal).getPrincipal());
                currentEmail = (String) oidcUser.getAttributes().get("email");
            }
            else{
                currentEmail = principal.getName();
            }
        }
        catch (Exception e){
            log.info("Unable to get current user Email", e);
        }
        return currentEmail;
    }
}
