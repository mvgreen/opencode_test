package com.mvgreen.opencode_test;

import com.mvgreen.opencode_test.entities.UserData;
import com.mvgreen.opencode_test.entities.UserAuthority;
import com.mvgreen.opencode_test.repositories.RoleRepository;
import com.mvgreen.opencode_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public DataLoader(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        UserAuthority commonUser = new UserAuthority();
        commonUser.setName("User");
        roleRepository.save(commonUser);

        UserAuthority admin = new UserAuthority();
        admin.setName("Admin");
        roleRepository.save(admin);

        UserData testUser = new UserData();
        testUser.setUsername("username");
        testUser.setPassword("password");

        userService.save(testUser);
    }
}