package com.zikozee.securityjwtjpa.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoles {

    STUDENT("STUDENT"),
    ADMIN("ADMIN"),
    ADMIN_TRAINEE("ADMIN_TRAINEE"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String role;

}
