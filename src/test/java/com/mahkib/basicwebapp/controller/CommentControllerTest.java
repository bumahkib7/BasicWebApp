package com.mahkib.basicwebapp.controller;


import com.mahkib.basicwebapp.models.Comment;
import com.mahkib.basicwebapp.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.yaml.snakeyaml.comments.CommentType;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class CommentControllerTest {


    @Autowired
    private MockMvc bigNuts;

    @MockBean
    @Autowired
    private CommentService commentService;


    @Test
    public void saveComments_HappyPath_ShouldReturnStatus302() throws Exception {
        //WHEN
        ResultActions resultActions = bigNuts
                .perform(post("/comment")
                        .with(csrf())
                        .with(user("user").roles("USER"))
                        .param("plusComment", "Test plus"));

        //THEN
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentService);
    }

    @Test
    public void getComments_HappyPath_ShouldReturnStatus200() throws Exception {
        //Given
        Comment comment = new Comment();
        comment.setComment("Test comment");
        comment.setType(CommentType.IN_LINE);
        comment.setCreatedBy("mahkib");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("Test comment2");
        comment2.setType(CommentType.BLOCK);
        comment2.setCreatedBy("shuraim");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> comments = Arrays.asList(comment, comment2);
        when(commentService.getAllCommentsForToday()).thenReturn(comments);

        //WHEN
        ResultActions resultAction = bigNuts
                .perform(get("/")
                        .with(user("mahkib")
                                .roles("USER")));


        //THEN
        resultAction
                .andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attribute("plusComments", hasSize(1)))
                .andExpect(model().attribute("plusComment", hasItem(
                        allOf(
                                hasProperty("createdBy", is("mahkib")),
                                hasProperty("comment", is("Test plus"))
                        ))
                ))
                .andExpect(model().attribute("starComments", hasSize(1)))
                .andExpect(model().attribute("starComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("shuraim")),
                                hasProperty("comment", is("Test comment2"))
                        ))
                ));


        verify(commentService, times(1)).getAllCommentsForToday();
        verifyNoMoreInteractions(commentService);


    }


}
