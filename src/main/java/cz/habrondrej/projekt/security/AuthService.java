package cz.habrondrej.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AuthService implements SecurityService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public String findLoggedInUser(String username) {
        Object userDetail = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetail instanceof UserDetails) {
            return ((UserDetails) userDetail).getUsername();
        }

        return null;
    }

    @Override
    public void autoLoggin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        authManager.authenticate(token);
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
