package com.mvgreen.opencode_test.repositories;

import com.mvgreen.opencode_test.entities.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserAuthority, Long> {
    UserAuthority findByName(String name);
}
