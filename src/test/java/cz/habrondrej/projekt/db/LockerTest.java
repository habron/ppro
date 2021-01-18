package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.utils.LockerHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LockerTest {

    @Autowired
    private LockerService lockerService;

    @Test
    public void findCustomerById() throws Exception {
         List<LockerHistory> list = lockerService.getHistory(1);

        for (LockerHistory locker:list) {
            assertEquals(1, locker.getEntityID());
        }
    }
}
