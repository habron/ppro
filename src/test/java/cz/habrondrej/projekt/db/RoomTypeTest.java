package cz.habrondrej.projekt.db;

import cz.habrondrej.projekt.model.Customer;
import cz.habrondrej.projekt.model.RoomType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RoomTypeTest {

    @Autowired
    private RoomTypeService roomTypeService;

    @Test
    public void findCustomerById() throws Exception {
        RoomType roomType = roomTypeService.findById(1);
        assertEquals("Zrcadlový sál", roomType.getName());
        assertEquals("Tělocvična se zrcadly", roomType.getDescription());
    }
}
