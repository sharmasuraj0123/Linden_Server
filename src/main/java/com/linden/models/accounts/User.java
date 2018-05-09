package com.linden.models.accounts;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User extends Account implements Serializable {

    private boolean verifiedAccount;

    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.GUEST;

    @ElementCollection
    @CollectionTable(
            name="want_to_see"
    )
    @OneToMany
    private Set<UserWantsToSee> wantsToSee;

    public User(){
        super();
    }

    public User(String email, String password){
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

    public Set<UserWantsToSee> getWantsToSee() {
        return wantsToSee;
    }

    public void setWantsToSee(Set<UserWantsToSee> wantsToSee) {
        this.wantsToSee = wantsToSee;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            User user = (User) obj;
            return user.email.equals(this.email) && user.password.equals(this.password);
        }
        else return false;
    }
}
