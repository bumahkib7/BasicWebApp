package com.mahkib.basicwebapp.services;

import com.mahkib.basicwebapp.models.Comment;
import com.mahkib.basicwebapp.repo.CommentRepo;
import com.mahkib.basicwebapp.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.yaml.snakeyaml.comments.CommentType;

import javax.annotation.meta.When;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)

public class CommentServiceTest {

    @MockBean
    private CommentRepo commentRepo;

    private CommentService commentService;

    @Before
    public void init() {
        commentService = new CommentService(commentRepo);
    }

    @Test
    public void getAllCommentsForToday_HappyPath_ShouldReturn1Comment() {
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
        List<Comment> actualComments = commentService.getAllCommentsForToday();


        // Then
        verify(commentRepo).findByCreatedYearAndMonthAndDay(now.getYear(),
                now.getMonthValue(),
                now.getDayOfMonth());
        assertThat(comments).isEqualTo(actualComments);
    }


    @Test
    public void save_HappyPath_shouldSave2Comments() {
        // Given
        Comment comment = new Comment();
        comment.setComment("This is a comment to save");
        comment.setType(CommentType.IN_LINE);
        comment.setCreatedBy("mahkib");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("This is a comment to save");
        comment2.setType(CommentType.IN_LINE);
        comment2.setCreatedBy("shuraim");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> comments = List.of(comment);
        when(commentRepo.saveAll(comments)).thenReturn(comments);

        // When
        List<Comment> saved = commentService.saveAll(comments);

        // Then
        assert saved != null;
        verify(commentRepo, times(1)).saveAll(comments);
    }

}
