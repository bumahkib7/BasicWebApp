package com.mahkib.basicwebapp.repo;

import com.mahkib.basicwebapp.models.Comment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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


@RunWith(SpringRunner.class)
@DataJpaTest
@AllArgsConstructor
@NoArgsConstructor

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
        testEntityManager.persist(comment);
        testEntityManager.flush();

        //When
        LocalDate localDate = LocalDate.now();
        List<Comment> comments =
                commentRepo.findByCreatedYearAndMonthAndDay(localDate.getYear(),
                        localDate.getMonthValue(),
                        localDate.getDayOfMonth()
                );


        //Then
        assertThat(comments.size(), is(1));
        assertThat(comments.get(0).getComment(), is("This is a comment"));


    }



    @Test
    public void save_HappyPath_shouldSave1Comment() {
        //Given
        Comment comment = new Comment();
        comment.setComment("This is a Test Comment");
        comment.setType(CommentType.IN_LINE);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));


        //When
        Comment savedComment = commentRepo.save(comment);

        //Then
        assertThat(testEntityManager.find(Comment.class, savedComment.getId()), is(savedComment));
    }


}
