package com.zikozee.securityjwtjpa.jwt;

import com.google.common.base.Strings;
import com.zikozee.securityjwtjpa.Exceptions.AuthenticationException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenVerifier extends OncePerRequestFilter {//invoked once per every request

    private final JwtConfig jwtConfig;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

        try{
            if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {  //reject request
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Collection<? extends GrantedAuthority> simpleGrantedAuthorities = jwtTokenUtil.getAuthorities(token);

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (IllegalArgumentException e) {
            throw new AuthenticationException("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException("JWT_TOKEN_EXPIRED", e);
        }

        filterChain.doFilter(request, response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());//i.e get the value part of Key : AUTHORIZATION
//
//        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {  //reject request
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), ""); //stripping off the Bearer<SPACE> part
//
//        try {
//            Jws<Claims> claimsJws = Jwts.parserBuilder()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//
//            //get body or authorities we passed in JwtUsernameAndPasswordAuthenticationFilter.successfulAuthentication
//            Claims body = claimsJws.getBody();
//            //getting username on login
//            String username = body.getSubject();
//            //getting our authorities
//            var authorities = (List<Map<String, String>>) body.get("authorities");
//            log.info("username: " + body.getSubject() + " name another version " + body.get("sub"));
//            log.info("expiryDate: " + body.getExpiration() + " expiryDate another version" + body.get("iat"));
//
//            //converting authorities to simpleGrantedAuthorities
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
//                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
//                    .collect(Collectors.toSet());
//
//            //Initializing authentication with username and its authorities
//            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
//            //setting currently logged in user with its authorities. Hence client that sends request is now authenticated
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
//        } catch (ExpiredJwtException e) {
//            throw new AuthenticationException("JWT_TOKEN_EXPIRED");
//        }
//
//        //This ensures that the request and response gotten as request Params is passed on to the next filter
//        filterChain.doFilter(request, response);
//    }
}
