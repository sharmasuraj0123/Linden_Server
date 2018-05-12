package com.linden.repositories;

import com.linden.models.accounts.User;
import com.linden.models.accounts.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserType(UserType userType);

    List<User> findUserByUserTypeOrUserType(String type1, String type2);

    List<User> findUserByUserType(String type);


}
