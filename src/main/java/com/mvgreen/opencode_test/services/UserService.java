package com.mvgreen.opencode_test.services;

import com.mvgreen.opencode_test.entities.UserData;

import java.util.List;

public interface UserService {

    void save(UserData user);

    UserData findByUsername(String username);

    void grantRole(String username, String role);

    float updateScore(String username, int attempts);

    List<UserData> getHighScoreTable();
}
