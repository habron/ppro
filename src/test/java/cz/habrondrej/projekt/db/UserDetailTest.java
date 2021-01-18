package cz.habrondrej.projekt.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserDetailTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void findCustomerById() throws Exception {
        UserDetails user = userDetailsService.loadUserByUsername("pavel");
        assertEquals("pavel", user.getUsername());

        assertThrows(UsernameNotFoundException.class, ()  -> userDetailsService.loadUserByUsername("nobody"));

    }
}
