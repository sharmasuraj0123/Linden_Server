package com.linden.models;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Admin extends Account implements Serializable {
    private double salary;

    public Admin(){
        super();
    }

    public Admin(String email, String password){
        super(email, password);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
