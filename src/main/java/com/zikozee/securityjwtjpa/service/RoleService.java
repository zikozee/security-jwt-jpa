package com.zikozee.securityjwtjpa.service;

import com.zikozee.securityjwtjpa.Domain.Role;

public interface RoleService {

    Role findByName(String name);

    Role save(Role role);
}
