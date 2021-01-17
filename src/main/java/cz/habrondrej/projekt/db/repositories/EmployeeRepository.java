package cz.habrondrej.projekt.db.repositories;

import cz.habrondrej.projekt.model.Employee;
import cz.habrondrej.projekt.model.User;

@org.springframework.stereotype.Repository
public interface EmployeeRepository extends UserRepository<Employee> {
    void update(User user);
}
