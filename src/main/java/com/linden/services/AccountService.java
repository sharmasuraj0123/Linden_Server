package com.linden.services;

import com.linden.models.Account;
import com.linden.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account getAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public boolean checkCredentials(Account account, Account databaseAccount){
        return account.getEmail().equals(databaseAccount.getEmail())
                && account.getPassword().equals(databaseAccount.getPassword());
    }

    public boolean checkCredentials(Account account){
        return checkCredentials(account, getAccountByEmail(account.getEmail()));
    }
}
