package com.mvgreen.opencode_test.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(columnDefinition = "integer default 0")
    private Integer sumOfAttempts = 0;

    @Column(columnDefinition = "integer default 0")
    private Integer amountOfGames = 0;

    @Transient
    private Float score = (float) 0;

    @ManyToMany
    private Set<UserRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Integer getSumOfAttempts() {
        return sumOfAttempts;
    }

    public void setSumOfAttempts(Integer sumOfAttempts) {
        this.sumOfAttempts = sumOfAttempts;
    }

    public Integer getAmountOfGames() {
        return amountOfGames;
    }

    public void setAmountOfGames(Integer amountOfGames) {
        this.amountOfGames = amountOfGames;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
