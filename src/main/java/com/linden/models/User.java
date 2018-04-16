package com.linden.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User extends Account implements Serializable {

    private boolean verifiedAccount;
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.GUEST;

    public User() {
        super();
    }

    public User(String email, String password) {
        super(email, password);
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
