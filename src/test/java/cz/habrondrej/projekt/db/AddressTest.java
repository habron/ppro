package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.utils.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void findCustomerById() throws Exception {
        Address address = addressService.findById(5);
        assertEquals("Náchod", address.getCity());
        assertEquals("Česko", address.getCountry());
        assertEquals("54701", address.getZip());
    }
}
