package com.mahkib.basicwebapp.repo;

import com.mahkib.basicwebapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c WHERE year (c.createdDate) = ?1 AND month (c.createdDate) = ?2 AND day (c.createdDate) = ?3")
    List<Comment> findByCreatedYearAndMonthAndDay(int year, int month, int day);

}
