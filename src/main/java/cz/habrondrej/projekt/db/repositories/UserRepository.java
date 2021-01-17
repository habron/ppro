package cz.habrondrej.projekt.db.repositories;

public interface UserRepository<T> extends Repository<T> {

    T findUserByUsername(String username);
}
