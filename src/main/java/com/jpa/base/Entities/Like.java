package com.jpa.base.Entities;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    public Like() {
    }
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "uid", insertable = false, updatable = false)
    private User user;
    public Like(int lid, int tid, int uid) {
        this.lid = lid;
        this.tid = tid;
        this.uid = uid;
    }
    public int getLid() {
        return lid;
    }
    public void setLid(int lid) {
        this.lid = lid;
    }
    @Override
    public String toString() {
        return "Like{" +
                "user=" + user +
                ", lid=" + lid +
                ", tid=" + tid +
                ", uid=" + uid +
                '}';
    }
    public int getTid() {
        return tid;
    }
    public void setTid(int tid) {
        this.tid = tid;
    }
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int lid;
    private int tid;
    private int uid;
    public User getUser() {
        return user;
    }
    public void setUser(User follower) {
        this.user = follower;
    }
}
