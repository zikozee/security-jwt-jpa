package com.zikozee.securityjwtjpa.Domain.token;

import com.zikozee.securityjwtjpa.Domain.user.User;
import com.zikozee.securityjwtjpa.Domain.user.UserService;
import com.zikozee.securityjwtjpa.Exceptions.AuthenticationException;
import com.zikozee.securityjwtjpa.Exceptions.UserNotFoundException;
import com.zikozee.securityjwtjpa.jwt.JwtConfig;
import com.zikozee.securityjwtjpa.jwt.JwtTokenRequestDTO;
import com.zikozee.securityjwtjpa.jwt.JwtTokenResponseDTO;
import com.zikozee.securityjwtjpa.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private final JwtConfig jwtConfig;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;


    @Override
    public TokenDTO create(TokenDTO tokenDTO) {
        Token token = modelMapper.map(tokenDTO, Token.class);
        Token generatedToken = tokenRepository.save(token);
        return modelMapper.map(generatedToken, TokenDTO.class);
    }

    @Override
    public TokenDTO findByBlacklistedToken(String blackListedToken) {
        return null;
    }

    @Override
    public UpdateTokenDTO update(UpdateTokenDTO updateTokenDTO) {
        Token token = modelMapper.map(updateTokenDTO, Token.class);
        Token generatedToken = tokenRepository.save(token);
        return modelMapper.map(generatedToken, UpdateTokenDTO.class);
    }


    @Override
    public String findTokenByUsername(String username) {
        if (!userService.userExists(username))
            throw new UserNotFoundException("User with username: " + username + " not found");

        Token token = userService.findByUsername(username).getToken();
        if (token == null) return null;

        return TokenUtil.stringToken(token.getTokenSecret());
    }

    @Override
    public ResponseEntity<?> createAuthenticationToken(JwtTokenRequestDTO jwtTokenRequestDTO) {

        Authentication authentication = this.authenticate(jwtTokenRequestDTO);

        String newToken;
        String existingToken = this.findTokenByUsername(jwtTokenRequestDTO.getUsername());
        if (existingToken == null) {
            newToken = jwtTokenUtil.generateToken(jwtTokenRequestDTO.getUsername(), authentication.getAuthorities());

            TokenDTO tokenDTO = TokenDTO.builder()
                    .tokenSecret(TokenUtil.byteToken(newToken))
                    .user(userService.findByUsername(jwtTokenRequestDTO.getUsername()))
                    .build();

            this.create(tokenDTO);


            return ResponseEntity.ok(new JwtTokenResponseDTO(newToken));

        } else {
            boolean expired = jwtTokenUtil.isTokenExpired(existingToken);
            if (expired) {
                newToken = jwtTokenUtil.generateToken(jwtTokenRequestDTO.getUsername(), authentication.getAuthorities());

                User user =userService.findByUsername(jwtTokenRequestDTO.getUsername());

                UpdateTokenDTO updateTokenDTO = UpdateTokenDTO.builder()
                        .id(user.getId())
                        .user(user)
                        .tokenSecret(TokenUtil.byteToken(newToken))
                        .blackListed(false)
                        .build();

                this.update(updateTokenDTO);


                return ResponseEntity.ok(new JwtTokenResponseDTO(newToken));
            }
            return ResponseEntity.ok(new JwtTokenResponseDTO(existingToken));
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader(jwtConfig.getAuthorizationHeader());
        final String token = authToken.replace(jwtConfig.getTokenPrefix(), "");

        boolean expired = jwtTokenUtil.isTokenExpired(token);
        if (!expired) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);

            String username = jwtTokenUtil.getUsernameFromToken(token);

            User user = userService.findByUsername(username);
            Token gottenToken = user.getToken();
            gottenToken.setTokenSecret(TokenUtil.byteToken(refreshedToken));
            user.setToken(gottenToken);
            userService.save(user);

            return ResponseEntity.ok(new JwtTokenResponseDTO(refreshedToken));
        } else {
            throw new AuthenticationException("TOKEN_EXPIRED RE-LOGIN");
        }
    }

    @Override
    public void invalidateToken(HttpServletRequest request) {
        String authToken = request.getHeader(jwtConfig.getAuthorizationHeader());
        final String token = authToken.replace(jwtConfig.getTokenPrefix(), "");

        String invalidatedToken = jwtTokenUtil.invalidateToken(token);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.findByUsername(username);
        Token gottenToken = user.getToken();
        gottenToken.setTokenSecret(TokenUtil.byteToken(invalidatedToken));
        gottenToken.setBlackListed(true);
        user.setToken(gottenToken);
        userService.save(user);

        log.info("TOKEN:>> " + invalidatedToken);

    }

    private Authentication authenticate(JwtTokenRequestDTO requestDTO) {
        Objects.requireNonNull(requestDTO.getUsername());
        Objects.requireNonNull(requestDTO.getPassword());


        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));
            log.info("Authentication status: -> " + authentication.isAuthenticated());
            return authentication;

        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
            throw new AuthenticationException("SOMETHING WENT WRONG", e);
        }

    }


}
