package com.caesar.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseIdEntity implements UserDetails {

    private static final long serialVersionUID = 10L;

    private String email;
    private String username;
    private String password;
    private Boolean enabled;

    @Column(name = "account_locked")
    private Boolean accountNonLocked;

    @Column(name = "account_expired")
    private Boolean accountNonExpired;

    @Column(name = "credentials_expired")
    private Boolean credentialsNonExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private List<Role> roles;

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountNonLocked;
    }

    /*
     * Get roles and permissions as a Set of GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        roles.forEach(role -> {
            authorities.add(role);
            authorities.addAll(role.getPermissions());
        });

        return authorities;
    }
}
