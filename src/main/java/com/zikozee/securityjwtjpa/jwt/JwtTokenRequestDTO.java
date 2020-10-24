package com.zikozee.securityjwtjpa.jwt;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class JwtTokenRequestDTO implements Serializable {

    public static final long serialVersionUID = -561617689701310834L;

    private String username;
    private String password;

}
