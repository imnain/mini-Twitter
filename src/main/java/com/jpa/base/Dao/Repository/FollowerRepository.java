package com.jpa.base.Dao.Repository;

import com.jpa.base.Dao.Entities.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

import java.util.List;

@Transactional
public interface FollowerRepository extends JpaRepository<Followers, Integer> {
    @Query(value = "select * from followers where fid =:n ", nativeQuery = true)
    public List<Followers> getFollowerByUser(@Param("n") int userId);

    @Query(value = "select * from followers where sid =:n ", nativeQuery = true)
    public List<Followers> getFollowersByUser(@Param("n") int userId);
    @Query(value = "select * from followers where fid =:n AND sid=:m ", nativeQuery = true)
    public List<Followers> isfollow(@Param("n") int userId, @Param("m") int user2Id);
    @Query(value = "DELETE FROM followers WHERE fid =:n AND sid=:m ", nativeQuery = true)
    @Modifying
    public void isunfollow(@Param("n") int userId, @Param("m") int user2Id);

}