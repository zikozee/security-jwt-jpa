package com.zikozee.securityjwtjpa.jwt;

import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtTokenResponseDTO implements Serializable {

    private static final long serialVersionUID = 83176762192979109L;

    private final String token;

}
