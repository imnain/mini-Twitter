package com.jpa.base.Dao.Repository;

import com.jpa.base.Dao.Entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.util.List;

@Transactional
public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query(value = "select * from likes where tid =:n AND uid=:m ", nativeQuery = true)
    public List<Like> isliked(@Param("n") int userId, @Param("m") int user2Id);

    @Query(value = "DELETE FROM likes WHERE tid =:n AND uid=:m ", nativeQuery = true)
    @Modifying
    public void isunliked(@Param("n") int userId, @Param("m") int user2Id);

    @Query(value = "select * from likes where tid=:n", nativeQuery = true)
    public List<Like> likeCounter(@Param("n") int userId);
}