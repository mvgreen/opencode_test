package com.mvgreen.opencode_test.entities;

import javax.persistence.*;

@Entity
public class UserAuthority {

    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
