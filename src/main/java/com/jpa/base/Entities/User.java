package com.jpa.base.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(targetEntity = Tweet.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    //@JoinColumn(name="user_id" , referencedColumnName = "id")
    private List<Tweet> tweet = new ArrayList<>();

    public List<Tweet> getTweet() {
        return tweet;
    }

    public void setTweet(List<Tweet> tweet) {
        this.tweet = tweet;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Followers> followers = new ArrayList<>();

    public List<Followers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Followers> followers) {
        this.followers = followers;
    }



    public User() {
    }

    public User(int id, String email, String role, String bio, String name, String password) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
        this.bio = bio;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }



    private String name;
    private String email;
    private String password;
    private String role;
    private String bio;

}
