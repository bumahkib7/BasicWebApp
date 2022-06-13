package com.mahkib.basicwebapp.repo;

import com.mahkib.basicwebapp.models.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.comments.CommentType;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@DataJpaTest


public class CommentRepoTest {


    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepo commentRepo;

  @Test
    public void findByCreatedYearAndMonthAndDay_HappyPath_ShouldReturn1Comment() {
      // Given
      Comment comment = new Comment();
      comment.setComment("This is a comment");
      comment.setType(CommentType.IN_LINE);
      comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
      List<Comment> comments = List.of(comment);

      LocalDate now = LocalDate.now();
      when(commentRepo.findByCreatedYearAndMonthAndDay(now.getYear(),
              now.getMonthValue(),
              now.getDayOfMonth())).thenReturn(comments);

      // When
      List<Comment> actualComments = commentRepo.findByCreatedYearAndMonthAndDay(now.getYear(),
              now.getMonthValue(),
              now.getDayOfMonth());

      // Then
      verify(commentRepo).findByCreatedYearAndMonthAndDay(now.getYear(),
              now.getMonthValue(),
              now.getDayOfMonth());
      assertThat(comments, is(actualComments));

  }


}
