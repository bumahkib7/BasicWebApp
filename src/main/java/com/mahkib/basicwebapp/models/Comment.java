package com.mahkib.basicwebapp.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.yaml.snakeyaml.comments.CommentType;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@EntityListeners({ AuditingEntityListener.class })
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @Enumerated(EnumType.STRING)
    private CommentType type;

    @CreatedDate
    private Timestamp createdDate;

    @CreatedBy
    private String createdBy;

}

// End of file Comment.java
// Location: src/main/java/com/mahkib/basicwebapp/models/Comment.java
