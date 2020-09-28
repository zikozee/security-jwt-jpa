package com.zikozee.securityjwtjpa.Exceptions;

public class UserRoleNotFoundException extends RuntimeException{
    public UserRoleNotFoundException() {
        super();
    }

    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
