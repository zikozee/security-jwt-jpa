package com.zikozee.securityjwtjpa.Domain.token;


import java.nio.charset.StandardCharsets;


public class TokenUtil {
    private TokenUtil() {}

    public static String stringToken(byte[] tokenBytes){
        return new String(tokenBytes, StandardCharsets.ISO_8859_1);
    }

    public static byte[] byteToken(String token){
        return token.getBytes(StandardCharsets.ISO_8859_1);
    }
}
