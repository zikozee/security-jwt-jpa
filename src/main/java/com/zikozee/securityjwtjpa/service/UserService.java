package com.zikozee.securityjwtjpa.service;

import com.zikozee.securityjwtjpa.Domain.User;

public interface UserService {
    User findByUsername(String username);

    User save(User user);

    void deleteById(Long adminId);
}
