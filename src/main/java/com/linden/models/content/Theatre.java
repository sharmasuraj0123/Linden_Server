package com.linden.models.content;

import javax.persistence.*;

@Entity
public class Theatre {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    @Column(name="name", unique=true)
    protected String name;

    public Theatre(){}

    public Theatre(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
