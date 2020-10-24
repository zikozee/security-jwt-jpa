package com.zikozee.securityjwtjpa.repo;

import com.zikozee.securityjwtjpa.Domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
}
