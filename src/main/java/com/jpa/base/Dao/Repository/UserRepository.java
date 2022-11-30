package com.jpa.base.Dao.Repository;

import com.jpa.base.Dao.Entities.User;
import com.jpa.base.Dao.Entities.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
    @Query(value = "select * from user where email =:email", nativeQuery = true)
    public List<User> getUserByEmail(@Param("email") String email);
    @Query("select u from User u where u.id = :id")
    public User getUserByUserId(@Param("id") int id);
    @Query("select new com.jpa.base.Dao.Entities.OrderResponse( u.name , t.description, t.tId) FROM User u JOIN u.tweet t")
    public List<OrderResponse> getJoinInformation();
    @Query("select new com.jpa.base.Dao.Entities.OrderResponse( u.name , t.description, t.tId) FROM User u JOIN u.tweet t where t.tId = :tId")
    public List<OrderResponse> getLike(@Param("tId") int tId);

}
