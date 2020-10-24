package com.zikozee.securityjwtjpa.Domain.user;

import com.zikozee.securityjwtjpa.Domain.role.Role;
import com.zikozee.securityjwtjpa.Domain.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@SQLDelete(sql = "Update users SET deleted = 1 where id=?")
@Where(clause = "deleted <> 1")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)// unique = true
    private String username;

    @Column(length = 68)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean activated;

    @Column(nullable = false)
    private boolean accountExpired;

    @Column(nullable = false)
    private boolean deleted;

    //Implementing with a shared primary key
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Token token;



    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
        super();
        this.enabled = false;
        this.activated = false;
        this.accountExpired= false;
        this.deleted = false;
    }
}
