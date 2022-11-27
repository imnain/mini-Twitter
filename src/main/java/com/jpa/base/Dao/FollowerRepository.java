package com.jpa.base.Dao;

import com.jpa.base.Entities.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Followers, Integer> {
    @Query(value = "select * from followers where fid =:n ", nativeQuery = true)
    public List<Followers> getFollowerByUser(@Param("n") int userId);

    @Query(value = "select * from followers where sid =:n ", nativeQuery = true)
    public List<Followers> getFollowersByUser(@Param("n") int userId);
}