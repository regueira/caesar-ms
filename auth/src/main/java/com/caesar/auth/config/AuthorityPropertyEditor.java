package com.caesar.auth.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.beans.PropertyEditorSupport;

public class AuthorityPropertyEditor extends PropertyEditorSupport {

    private GrantedAuthority grantedAuthority;

    @Override
    public Object getValue() {
        return grantedAuthority;
    }

    @Override
    public void setValue(Object value) {
        this.grantedAuthority = (GrantedAuthority) value;
    }

    @Override
    public String getAsText() {
        return grantedAuthority.getAuthority();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && !text.isEmpty()) {
            this.grantedAuthority = new SimpleGrantedAuthority(text);
        }
    }
}
