package com.mahkib.basicwebapp.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String role;

}
// End of file User.java
// Location: src/main/java/com/mahkib/basicwebapp/models/User.java
