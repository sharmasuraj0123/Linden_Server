package com.linden.repositories;

import com.linden.models.accounts.AccountToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTokenRepository extends JpaRepository<AccountToken, Long> {
    List<AccountToken> findByToken(String token);
}
