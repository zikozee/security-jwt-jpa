package com.zikozee.securityjwtjpa.jwt;

import com.google.common.net.HttpHeaders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.jwt") //read https://docs.spring.io/spring-boot/docs/2.3.3.RELEASE/reference/html/appendix-configuration-metadata.html#configuration-metadata-annotation-processor
@NoArgsConstructor @Getter @Setter
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterSeconds;// now works //read https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-external-config.html#boot-features-external-config-typesafe-configuration-properties


    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
