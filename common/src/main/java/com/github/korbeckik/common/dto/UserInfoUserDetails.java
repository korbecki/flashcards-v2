package com.github.korbeckik.common.dto;

import com.github.korbeckik.common.entity.RolesEntity;
import com.github.korbeckik.common.entity.UsersEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserInfoUserDetails implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;


    private final String name;

    private final String surname;

    private final String userName;

    private final List<? extends GrantedAuthority> authorities;

    public UserInfoUserDetails(UsersEntity userInfo) {
        email = userInfo.getEmail();
        id = userInfo.getId();
        password = userInfo.getPassword();
        name = userInfo.getName();
        surname = userInfo.getSurname();
        userName = userInfo.getUserName();
        authorities = userInfo.getRoles()
                .stream()
                .map(RolesEntity::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}