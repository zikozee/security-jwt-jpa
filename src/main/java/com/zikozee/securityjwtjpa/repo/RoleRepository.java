package com.zikozee.securityjwtjpa.repo;

import com.zikozee.securityjwtjpa.Domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);

    Role findByName(String name);
}
