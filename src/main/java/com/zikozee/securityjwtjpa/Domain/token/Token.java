package com.zikozee.securityjwtjpa.Domain.token;

import com.zikozee.securityjwtjpa.Domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;


import javax.persistence.*;

@Entity
@Table(name = "token")
@Data
@AllArgsConstructor
public class Token {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    @Lob
    private byte[] tokenSecret;// TESTER project for conversion

    @Column(name = "black_list")
    private boolean blackListed;

    @OneToOne
    @MapsId
    private User user;

    public Token() {

        this.blackListed = false;
    }
}
