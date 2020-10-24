package com.zikozee.securityjwtjpa.Domain.token;

import com.zikozee.securityjwtjpa.Domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO{
    private byte[] tokenSecret;
    private User user;

}
