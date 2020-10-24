package com.zikozee.securityjwtjpa.controller.jwtController;

import com.zikozee.securityjwtjpa.Domain.token.JwtTokenService;
import com.zikozee.securityjwtjpa.jwt.JwtTokenRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtAuthenticationController {

    private final JwtTokenService jwtTokenService;


    @PostMapping(value = "${application.jwt.tokenUri}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtTokenRequestDTO jwtTokenRequestDTO){

        return jwtTokenService.createAuthenticationToken(jwtTokenRequestDTO);

    }

    @PostMapping(value = "${application.jwt.refreshUri}")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){

        return jwtTokenService.refreshToken(request);
    }


    @PostMapping(value = "${application.jwt.signOut}")
    public ResponseEntity<?> signOut(HttpServletRequest request){

        jwtTokenService.invalidateToken(request);
        return ResponseEntity.ok("Signed Out");
    }

}
