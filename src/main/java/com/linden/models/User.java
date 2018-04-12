package com.linden.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User extends Account implements Serializable {

    private String username;
    private boolean verifiedAccount;
    private UserType userType = UserType.GUEST;

    public User(){
        super();
    }

    public User(String email, String password){
        super(email, password);
    }

    public User(String email, String password, String username){
        this(email, password);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isVerifiedAccount() {
        return verifiedAccount;
    }

    public void setVerifiedAccount(boolean verifiedAccount) {
        this.verifiedAccount = verifiedAccount;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
