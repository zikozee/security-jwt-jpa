package com.zikozee.securityjwtjpa.setUp;

import io.jsonwebtoken.Clock;

import java.util.Date;

public class Test {
    public static void main(String[] args) {

        Clock clock = Date::new;
        System.out.println(clock.now());
    }
}
