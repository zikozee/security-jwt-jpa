package com.zikozee.securityjwtjpa.jwt;

import com.zikozee.securityjwtjpa.Exceptions.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;


    public String getUsernameFromToken(String token) {
        return getSingleClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getSingleClaimFromToken(token, Claims::getIssuedAt);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        var authorities = (List<Map<String, String>>)getAllClaimsFromToken(token).get("authorities");
        return authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                .collect(Collectors.toSet());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public <T> T getSingleClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        final Date createdDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.set(Calendar.SECOND, jwtConfig.getTokenExpirationAfterSeconds());
        final Date expirationDate = calendar.getTime();

        return Jwts.builder()
                .setSubject(subject)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
//                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()))) //duration
                .setExpiration(expirationDate) //duration
                .signWith(secretKey) //signature
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                 .parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        try{
            final Date now = Calendar.getInstance().getTime();
            final Date expiration = getExpirationDateFromToken(token);
            return !expiration.before(now);
        }catch (ExpiredJwtException e){
            return true;
        }
    }


    public String refreshToken(String token) {
        final Date createdDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdDate);
        calendar.set(Calendar.SECOND, jwtConfig.getTokenExpirationAfterSeconds());
//        final Date expirationDate = java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays()));
        final Date expirationDate = calendar.getTime();



        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public String invalidateToken(String token){
        final Date expirationDate = Calendar.getInstance().getTime();

        final Claims claims = getAllClaimsFromToken(token);
        claims.setExpiration(expirationDate);
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }


}
