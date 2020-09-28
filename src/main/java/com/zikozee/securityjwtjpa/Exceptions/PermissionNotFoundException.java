package com.zikozee.securityjwtjpa.Exceptions;

public class PermissionNotFoundException extends RuntimeException{
    public PermissionNotFoundException() {
        super();
    }

    public PermissionNotFoundException(String message) {
        super(message);
    }
}
