package com.zikozee.securityjwtjpa.Domain.role;

import com.zikozee.securityjwtjpa.Domain.role.Role;

public interface RoleService {

    Role findByName(String name);

    Role save(Role role);
}
