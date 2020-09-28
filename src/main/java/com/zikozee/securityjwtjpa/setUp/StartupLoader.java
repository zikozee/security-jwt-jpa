package com.zikozee.securityjwtjpa.setUp;

import com.google.common.collect.Sets;
import com.zikozee.securityjwtjpa.Domain.Permission;
import com.zikozee.securityjwtjpa.Domain.Role;
import com.zikozee.securityjwtjpa.Domain.User;
import com.zikozee.securityjwtjpa.repo.PermissionRepository;
import com.zikozee.securityjwtjpa.repo.RoleRepository;
import com.zikozee.securityjwtjpa.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

import static com.zikozee.securityjwtjpa.security.UserPermissions.*;
import static com.zikozee.securityjwtjpa.security.UserRoles.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartupLoader implements CommandLineRunner {
    private final UserRepository userRepository;//using repo cos of exit condition added in userServiceImpl
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private boolean alreadySetup = false;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(alreadySetup) return;

        // == Create user permissions
        Permission studentRead = createPermissionIfNotFound(STUDENT_READ.getPermission());
        Permission studentWrite = createPermissionIfNotFound(STUDENT_WRITE.getPermission());
        Permission courseRead = createPermissionIfNotFound(COURSE_READ.getPermission());
        Permission courseWrite = createPermissionIfNotFound(COURSE_WRITE.getPermission());
        Permission adminWrite = createPermissionIfNotFound(ADMIN_WRITE.getPermission());

        Role studentRole= createRoleIfNotFound(STUDENT.getRole(), Sets.newHashSet());
        Role adminRole= createRoleIfNotFound(ADMIN.getRole(), Sets.newHashSet(Arrays.asList(studentRead, studentWrite, courseRead, courseWrite)));
        Role adminTraineeRole= createRoleIfNotFound(ADMIN_TRAINEE.getRole(), Sets.newHashSet(Arrays.asList(courseRead, courseWrite)));
        Role superAdminRole= createRoleIfNotFound(SUPER_ADMIN.getRole(), Sets.newHashSet(Arrays.asList(studentRead, studentWrite, courseRead, courseWrite, adminWrite)));

        //== create default users
        User zikoUser = createUserIfNotFound("ziko", "ziko123",  Sets.newHashSet(studentRole));
        log.info("... user with username: " + zikoUser.getUsername()
                + " created successfully with no hassle" + " \uD83D\uDE0D \uD83D\uDE0D \uD83D\uDE0D");
        User lindaUser = createUserIfNotFound("linda", "password",  Sets.newHashSet(adminRole));//we could have added more roles to any user
        log.info("... user with username: " + lindaUser.getUsername()
                + " created successfully with no hassle" + " \uD83D\uDE0D \uD83D\uDE0D \uD83D\uDE0D");
        User tomUser = createUserIfNotFound("tom", "password", Sets.newHashSet(adminTraineeRole));
        log.info("... user with username: " + tomUser.getUsername()
                + " created successfully with no hassle" + " \uD83D\uDE0D \uD83D\uDE0D \uD83D\uDE0D");
        User debbyUser = createUserIfNotFound("debby", "password", Sets.newHashSet(superAdminRole));
        log.info("... user with username: " + debbyUser.getUsername()
                + " created successfully with no hassle" + " \uD83D\uDE0D \uD83D\uDE0D \uD83D\uDE0D");



        alreadySetup = true;
    }

    @Transactional
    public Permission createPermissionIfNotFound(final String name) {
        Permission permission = permissionRepository.findByName(name);
        if (permission == null) {
            permission = new Permission(name);
            permission = permissionRepository.save(permission);
        }
        return permission;
    }

    @Transactional
    public Role createRoleIfNotFound(final String name, final Set<Permission> permissions) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPermissions(permissions);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    public User createUserIfNotFound(final String username, final String password, final Set<Role> roles) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setDeleted(false);
            user.setEnabled(true);
            user.setActivated(true);
            user.setAccountExpired(false);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        return user;
    }
}
