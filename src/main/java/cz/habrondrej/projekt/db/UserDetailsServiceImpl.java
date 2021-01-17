package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.model.User;

import cz.habrondrej.projekt.model.utils.PdfUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = customerService.findUserByUsername(username);
        if (user == null) {
            user = employeeService.findUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found.");
            }
        }

        return new PdfUserDetails(user);
    }
}
