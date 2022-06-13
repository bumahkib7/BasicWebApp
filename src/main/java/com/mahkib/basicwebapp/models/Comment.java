package com.mahkib.basicwebapp.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.yaml.snakeyaml.comments.CommentType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table
@EntityListeners({ AuditingEntityListener.class})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return id != null && Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

// End of file Comment.java
// Location: src/main/java/com/mahkib/basicwebapp/models/Comment.java
