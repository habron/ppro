package cz.habrondrej.projekt.db.repositories;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository<T> {

    void insert(T t);
    void update(T t);
    void delete(T t);
    void deleteById(int id);
    List<T> getAll();
    T findById(int id);
}
