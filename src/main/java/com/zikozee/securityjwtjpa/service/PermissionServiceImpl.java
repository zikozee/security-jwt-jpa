package com.zikozee.securityjwtjpa.service;

import com.zikozee.securityjwtjpa.Exceptions.PermissionNotFoundException;
import com.zikozee.securityjwtjpa.Domain.Permission;
import com.zikozee.securityjwtjpa.repo.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService{
    private final PermissionRepository permissionRepository;

    @Override
    public Permission findByName(String name) {
        boolean permissionExists = permissionRepository.existsByName(name);
        if(!permissionExists) throw new PermissionNotFoundException("permission with name: " +name +" not found");

        return permissionRepository.findByName(name);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
}
