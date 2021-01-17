package cz.habrondrej.projekt.db.repositories;

import cz.habrondrej.projekt.model.utils.Login;

@org.springframework.stereotype.Repository
public interface LoginRepository extends Repository<Login> {

    void updateUsername(Login login);
    void updatePassword(Login login);
}
