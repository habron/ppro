package cz.habrondrej.projekt.db.repositories;

import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.User;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends UserRepository<Customer> {
    void update(User user);
}
