package cz.habrondrej.projekt.security;

public interface SecurityService {

    String findLoggedInUser(String username);
    void autoLoggin(String username, String password);
}
