package com.restteam.ong.models.impl;

import com.restteam.ong.models.Role;
import com.restteam.ong.models.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private boolean deleted;
    private Role role;

    public UserDetailsImpl(User user){
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.deleted = user.getDeleted();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> roles = new ArrayList<>();
        if(role != null){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return roles;
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
        return !deleted;
    }
}

