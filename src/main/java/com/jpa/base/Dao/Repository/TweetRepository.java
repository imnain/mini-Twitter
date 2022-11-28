package com.jpa.base.Dao.Repository;

import com.jpa.base.Dao.Entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import javax.websocket.server.PathParam;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    @Query("from Tweet as c WHERE c.user.id= :userId")
    public List<Tweet> getTweetByUser(@PathParam("user_id") int userId);
}