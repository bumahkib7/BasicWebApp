package com.mahkib.basicwebapp.service;

import com.mahkib.basicwebapp.models.Comment;
import com.mahkib.basicwebapp.repo.CommentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Comment>saveAll(List<Comment> comments) {
        logger.info("Saving {}", comments);
        return commentRepo.saveAll(comments);
    }

    public List<Comment> getAllCommentsForToday() {
        logger.info("Getting all comments for today");
        LocalDate localDate = LocalDate.now();
        return commentRepo.findByCreatedYearAndMonthAndDay(localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth());

    }
}
