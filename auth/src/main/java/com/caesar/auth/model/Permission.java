package com.caesar.auth.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseIdEntity implements GrantedAuthority {

    private static final long serialVersionUID = 20L;

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }

    public void setAuthority(String authority) {
        setName(authority);
    }

}
