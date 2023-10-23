package com.github.korbeckik.common.config;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

@EqualsAndHashCode(callSuper = false)
public class BearerToken extends AbstractAuthenticationToken {
    private final String token;

    public BearerToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return this.token;
    }

    @Override
    public String getPrincipal() {
        return this.token;
    }
}
