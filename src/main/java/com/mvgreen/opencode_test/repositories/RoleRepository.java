package com.mvgreen.opencode_test.repositories;

import com.mvgreen.opencode_test.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
}
