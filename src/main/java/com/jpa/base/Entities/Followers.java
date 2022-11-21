package com.jpa.base.Entities;

import javax.persistence.*;

@Entity
@Table(name = "followers")
public class Followers {
    public Followers() {
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "fid", insertable = false, updatable = false)
    private User user;



    public Followers(int id, int fid, int sid) {
        this.id = id;
        this.fid = fid;
        this.sid = sid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Followers{" +
                "user=" + user +
                ", id=" + id +
                ", fid=" + fid +
                ", sid=" + sid +
                '}';
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int fid;
    private int sid;



    public User getUser() {
        return user;
    }

    public void setUser(User follower) {
        this.user = follower;
    }
}
