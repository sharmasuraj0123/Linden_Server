package com.linden.repositories;

import com.linden.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByFirstName(String firstName);

    User findByLastName(String lastName);

    User findByFirstNameAndLastName(String firstName, String lastName);

    User findByEmail(String email);
}
