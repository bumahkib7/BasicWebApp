package com.mahkib.basicwebapp.repo;

import com.mahkib.basicwebapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User save(String username);

}
