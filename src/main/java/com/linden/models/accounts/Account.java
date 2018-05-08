package com.linden.models.accounts;

import javax.persistence.*;

@MappedSuperclass
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long accountId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;

    public Account(){

    }

    public Account(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}