package com.zikozee.securityjwtjpa.Domain.role;

import com.zikozee.securityjwtjpa.Exceptions.UserRoleNotFoundException;
import com.zikozee.securityjwtjpa.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        boolean roleExists = roleRepository.existsByName(name);
        if(!roleExists) throw new UserRoleNotFoundException("role with name: " + name + " not found");

        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
