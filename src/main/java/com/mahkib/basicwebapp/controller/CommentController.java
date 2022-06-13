package com.mahkib.basicwebapp.controller;


import com.mahkib.basicwebapp.models.Comment;
import com.mahkib.basicwebapp.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.comments.CommentType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommentController {

    private final static Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("index()");
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> allComments = commentService.getAllCommentsForToday();
        Map<CommentType, List<Comment>> groupedComments = allComments
                .stream()
                .collect(Collectors.groupingBy(Comment::getType));


        model.addAttribute("starComments", groupedComments.get(CommentType.IN_LINE));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.BLANK_LINE));
        model.addAttribute("plusComments", groupedComments.get(CommentType.BLOCK).size());

        return "comment";
    }

    @PostMapping("/comment")
    public String CreateComment(@RequestParam(name = "plusComment", required = false) String plusComment,
                                @RequestParam(name = "starComment", required = false) String starComment,
                                @RequestParam(name = "deltaComment", required = false) String deltaComment) {
        logger.info("CreateComment()");
        List<Comment> comments = new ArrayList<>();

        if (StringUtils.isNotEmpty(starComment)) {
            comments.add(getComment(starComment, CommentType.IN_LINE));
        }

        if (StringUtils.isNotEmpty(deltaComment)) {
            comments.add(getComment(deltaComment, CommentType.BLANK_LINE));
        }

        if (StringUtils.isNotEmpty(plusComment)) {
            comments.add(getComment(plusComment, CommentType.BLOCK));
        }

        if (!comments.isEmpty()) {
            logger.info("Saving {}", comments);
            commentService.saveAll(comments);
        }

        return "redirect:/";

    }

    private Comment getComment(String comment, CommentType commentType) {
        Comment commentObj = new Comment();
        commentObj.setType(commentType);
        commentObj.setComment(comment);
        return commentObj;
    }
}
