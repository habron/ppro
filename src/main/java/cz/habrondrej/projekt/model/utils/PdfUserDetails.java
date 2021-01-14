package cz.habrondrej.projekt.model.utils;

import cz.habrondrej.projekt.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class PdfUserDetails implements UserDetails {

    private User user;

    public PdfUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getRole())).collect(Collectors.toList());
    }

    public int getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getLogin().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin().getUsername();
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

    public String getRole() {

        for (Role role : user.getAuthorities()) {
            return role.getRole();
        }

        return "";
    }

    public User getUserDetails() {
        return user;
    }
}
