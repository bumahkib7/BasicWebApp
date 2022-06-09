package com.mahkib.basicwebapp.repo;

import com.mahkib.basicwebapp.models.User;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@NoArgsConstructor
public class UserRepoTest {


    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepo userRepo;

    @Test
    public void findByUsername_HappyPath_ShouldReturn1User() throws Exception {
        // Given
        User user = new User();
        user.setUsername("mahkib");
        user.setPassword("password123");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        //When
        User actual = userRepo.findByUsername("mahkib");

        //Then
        assertThat(actual).isNotNull();
    }


    @Test
    public void findByUsername_HappyPath_ShouldSave1User() throws Exception {
        // Given
        User user = new User();
        user.setUsername("mahkib");
        user.setPassword("password123");
        user.setRole("USER");


        //When
        User actual = userRepo.save("mahkib");

        //Then
        assertNotNull(actual);


    }

}
