package com.jpa.base.Entities;

import javax.persistence.*;

@Entity
@Table(name = "tweet")
public class Tweet {
    public Tweet() {
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public int gettId() {
        return tId;
    }
    public Tweet(int tId, String description) {
        this.tId = tId;
        this.description = description;
    }
    public void settId(int tId) {
        this.tId = tId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tId;
    @Column(length = 5000)
    private String description;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
