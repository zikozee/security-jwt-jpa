package com.zikozee.securityjwtjpa.controller.admin;

import com.zikozee.securityjwtjpa.Domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("management/api/v1/admin")
public class AdminController {
    //get Admins from db
    //delete the Admin in the below
    private final UserService userService;

    @DeleteMapping(path = "{adminId}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public void deleteAdmin(@PathVariable("adminId")Long adminId){
        log.info("deleteAdmin: deleting admin with id" + adminId);
        userService.deleteById(adminId);
    }

}
