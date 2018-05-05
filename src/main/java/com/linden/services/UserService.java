package com.linden.services;

import com.linden.models.Account;
import com.linden.models.User;
import com.linden.models.UserType;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public enum RegistrationStatus{
        OK, EMAIL_TAKEN
    }

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByType(UserType userType){
        return userRepository.findByUserType(userType);
    }

    public boolean checkCredentials(User user, Account databaseAccount){
        return user.getEmail().equals(databaseAccount.getEmail())
                && passwordEncoder.matches(user.getPassword(), databaseAccount.getPassword());
    }

    public boolean checkCredentials(User user){
        return checkCredentials(user, getUserByEmail(user.getEmail()));
    }

    @Transactional
    public RegistrationStatus registerUser(User user){
        user.setVerifiedAccount(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (getUserByEmail(user.getEmail()) != null || adminService.getAdminByEmail(user.getEmail()) != null) {
            return RegistrationStatus.EMAIL_TAKEN;
        }
        else {
            if(user.getUserType() == UserType.GUEST) {
                user.setUserType(UserType.AUDIENCE);
            }
            userRepository.save(user);
            return RegistrationStatus.OK;
        }
    }


}
