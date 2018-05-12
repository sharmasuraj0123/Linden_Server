package com.linden.services;

import com.linden.models.accounts.Account;
import com.linden.models.accounts.Admin;
import com.linden.models.accounts.User;
import com.linden.models.accounts.Verification;
import com.linden.repositories.AdminRepository;
import com.linden.repositories.UserRepository;
import com.linden.repositories.VerificationRepository;
import com.linden.util.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
public class VerificationService {
    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Value("${verification.token.length}")
    private int tokenLength;

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789";

    private static final Random random = new SecureRandom();

    public String generateToken(){
        StringBuilder buff = new StringBuilder();
        for (int i = 0; i < tokenLength; ++i) {
            buff.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return buff.toString();
    }

    public Verification generateVerification(User user) {
        Verification verification =  new Verification();
        verification.setAccountId(user.getId());
        verification.setToken(generateToken());
        verificationRepository.save(verification);
        return verification;
    }

    public String getUserVerificationToken(long userId) {
        List<Verification> tokenList = verificationRepository.findByAccountId(userId);
        if(tokenList.size() == 1) {
            return tokenList.get(0).getToken();
        }
        else {
            return null;
        }
    }

    public boolean verfiyAccount(long accountId, String token) {
        User user = userRepository.findById(accountId).orElse(null);
        if(user != null) {
            List<Verification> verificationList = verificationRepository.findByAccountId(accountId);
            if(verificationList.size() == 1) {
                Verification verification = verificationList.get(0);
                if(token.equals(verification.getToken())){
                    user.setVerifiedAccount(true);
                    userRepository.saveAndFlush(user);
                    verificationRepository.delete(verification);
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;
    }
}
