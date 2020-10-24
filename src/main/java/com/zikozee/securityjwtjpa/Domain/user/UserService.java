package com.zikozee.securityjwtjpa.Domain.user;

import com.zikozee.securityjwtjpa.Domain.user.User;

public interface UserService {
    User findByUsername(String username);

    User save(User user);

    void deleteById(Long adminId);

    boolean userExists(String username);

}
