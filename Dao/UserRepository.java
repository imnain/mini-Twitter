package com.jpa.base.Dao;

import com.jpa.base.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
    @Query("select u from User u where u.id = :id")
    public User getUserByUserId(@Param("id") int id);
}
