package com.linden.services;

import com.linden.models.accounts.Account;
import com.linden.models.accounts.AccountToken;
import com.linden.repositories.AccountTokenRepository;
import com.linden.repositories.AdminRepository;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
public class AccountTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountTokenRepository accountTokenRepository;

    private static final String ALPHABET = "ABCDEFGJKLMNPRSTUVWXYZ0123456789!@#$%^&";

    private static final Random random = new SecureRandom();

    @Value("${security.token.length:64}")
    private static int tokenLength;

    public AccountTokenService() {
    }

    public static String generateToken(){
        char[] buff = new char[tokenLength];
        for (int i = 0; i < buff.length; ++i) {
            buff[i] = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
        }
        return new String(buff);
    }

    public Account getAccount(String token) {
        List<AccountToken> accountTokens = accountTokenRepository.findByToken(token);
        if(accountTokens.size() == 1) {
            AccountToken accountToken = accountTokens.get(0);
            if(accountToken.isAdmin()) {
                return adminRepository.findById(accountToken.getAccountId()).orElse(null);
            }
            else {
                return userRepository.findById(accountToken.getAccountId()).orElse(null);
            }
        }
        else throw new SecurityException();
    }

    public String saveAccount(long accountId) {
        return saveAccount(accountId, false);
    }

    public String saveAccount(long accountId, boolean isAdmin){
        return saveAccount(generateToken(), accountId, isAdmin);
    }

    public String saveAccount(String token, long accountId) {
        return saveAccount(token, accountId, false);
    }

    public String saveAccount(String token, long accountId, boolean isAdmin){
        AccountToken accountToken = new AccountToken();
        accountToken.setAccountId(accountId);
        accountToken.setToken(token);
        accountToken.setAdmin(isAdmin);
        accountTokenRepository.save(accountToken);
        return token;
    }

    public void invalidateToken(String token) {
        List<AccountToken> accountTokens = accountTokenRepository.findByToken(token);
        if(accountTokens.size() == 1) {
            AccountToken accountToken = accountTokens.get(0);
            accountTokenRepository.delete(accountToken);
        }
        else throw new SecurityException();
    }
}
