package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAllByDateBetween(Date start, Date end);

    List<User> findByDate(Date date);

    @Query("SELECT u FROM User u WHERE DATE(u.date) = :searchDate")
    List<User> findByDateOnly(@Param("searchDate") Date date);
}
