package com.linden.repositories;

import com.linden.models.accounts.User;
import com.linden.models.accounts.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserType(UserType userType);
}
