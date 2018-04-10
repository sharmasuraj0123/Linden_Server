package com.linden.services;

import com.linden.models.User;
import com.linden.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public enum RegistrationStatus{
        OK, USERNAME_TAKEN, EMAIL_TAKEN
    }

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getUserByFirstName(String firstName){
        return userRepository.findByFirstName(firstName);
    }

    public User getUserByLastName(String lastName){
        return userRepository.findByFirstName(lastName);
    }

    public User getUserByFirstNameAndLastName(String firstName, String lastName){
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean verifyUserCredentials(String username, String password){
        User user = getUserByUsername(username);
        if(user.getUsername().equals(username))
            return true;
        else
            return false;
    }

    public RegistrationStatus registerUser(User user){
        if(getUserByUsername(user.getUsername()) == null) {
            return RegistrationStatus.USERNAME_TAKEN;
        }
        else if (getUserByEmail(user.getEmail()) != null) {
            return RegistrationStatus.EMAIL_TAKEN;
        }
        else {
            userRepository.save(user);
            return RegistrationStatus.OK;
        }
    }
}
