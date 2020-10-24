package com.zikozee.securityjwtjpa.Domain.token;

import com.zikozee.securityjwtjpa.jwt.JwtTokenRequestDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    String findTokenByUsername(String username);

    ResponseEntity<?> createAuthenticationToken(JwtTokenRequestDTO jwtTokenRequestDTO);

    ResponseEntity<?> refreshToken(HttpServletRequest request);

    void invalidateToken(HttpServletRequest request);



    TokenDTO create(TokenDTO tokenDTO);

    TokenDTO findByBlacklistedToken(String blackListedToken);

    UpdateTokenDTO update(UpdateTokenDTO updateTokenDTO);

}
