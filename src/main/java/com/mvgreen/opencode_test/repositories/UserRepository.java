package com.mvgreen.opencode_test.repositories;

import com.mvgreen.opencode_test.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserData, Long> {
    UserData findByUsername(String username);

    //List<UserData> findAll();
}
