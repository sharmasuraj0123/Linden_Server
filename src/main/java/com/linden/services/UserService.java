package com.linden.services;

import com.linden.models.User;
import com.linden.models.UserType;
import com.linden.repositories.AccountRepository;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public enum RegistrationStatus{
        OK, USERNAME_TAKEN, EMAIL_TAKEN
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByType(UserType userType){
        return userRepository.findByUserType(userType);
    }

    public boolean verifyUserCredentials(String username, String password){
        User user = getUserByUsername(username);
        if(user.getUsername().equals(username))
            return true;
        else
            return false;
    }

    public RegistrationStatus registerUser(User user){
        if(getUserByUsername(user.getUsername()) != null) {
            return RegistrationStatus.USERNAME_TAKEN;
        }
        else if (getUserByEmail(user.getEmail()) != null) {
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
