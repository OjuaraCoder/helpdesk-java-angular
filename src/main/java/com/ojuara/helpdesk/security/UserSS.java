package com.ojuara.helpdesk.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ojuara.helpdesk.enums.PerfilEnum;

public class UserSS implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Set<GrantedAuthority> authorities;

    public UserSS(Integer id, String email, String senha, Set<PerfilEnum> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.authorities = perfis.stream().map(x -> (GrantedAuthority) new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    public Integer getId(){
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return senha;
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
