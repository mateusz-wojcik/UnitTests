import org.junit.Test;

import static org.junit.Assert.*;

public class ClientTest {

    @Test
    public void testConstructor() {
        Client client = new Client("Mariusz", "Pudzianowski", "mar@gmail.com", "mar123", "silnehaslo");
        assertEquals("Mariusz", client.getName());
        assertEquals("Pudzianowski", client.getSurname());
        assertEquals("mar@gmail.com", client.getEmail());
        assertEquals("mar123", client.getUserName());
        assertEquals("silnehaslo", client.getPassword());
    }

}