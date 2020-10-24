package com.zikozee.securityjwtjpa.repo;

import com.zikozee.securityjwtjpa.Domain.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);

    Permission findByName(String name);
}
