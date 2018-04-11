package com.linden.repositories;

import com.linden.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByFirstName(String firstName);

    Account findByLastName(String lastName);

    Account findByFirstNameAndLastName(String firstName, String lastName);

    Account findByEmail(String email);
}
