package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findCustomerById() throws Exception {
        Customer customer = customerService.findById(3);
        assertEquals("test@test.com", customer.getEmail());
        assertEquals("František", customer.getFirstname());
        assertEquals("Holan", customer.getLastname());
    }
}
