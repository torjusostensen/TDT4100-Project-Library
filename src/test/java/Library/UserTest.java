package Library;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void checkPhoneNumber() {
        User user = new User("46464646", "1234");
        User admin = new User("99999999", "9999");

        // Quick check on getter and if valid
        assertEquals(user.getPhoneNumber(), "46464646");
        assertTrue(user.getPhoneNumber().matches("(^4|^9)\\d{7}"));

        // Check too short and too long phone numbers is not allowed
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("4535"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("32345"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("6666666"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("123456789"));


        // Check combination with letters
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("4a464646"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("4a46463542"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("abfwe463542"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("abcd"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("ad2948j4"));
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber("bokstaver"));

        // Check empty string
        assertThrows(IllegalArgumentException.class, () -> user.setPhoneNumber(""));

        // Check Admin
        assertEquals(admin.getPhoneNumber(), "99999999");
        assertTrue(admin.getPhoneNumber().matches("(^4|^9)\\d{7}"));
    }

    @Test
    void checkPin() {
        User user = new User("46464646", "1234");
        User admin2 = new User("99999999", "9999");

        // Quick check on getter and if valid
        assertEquals(user.getPin(), "1234");
        assertEquals(admin2.getPin(), "9999");

        // Check validation works through regex
        assertTrue(user.getPin().matches("\\d{4}"));
        assertTrue(admin2.getPin().matches("\\d{4}"));

        // Check faulty pin
        assertThrows(IllegalArgumentException.class, () -> user.setPin("124"));
        assertThrows(IllegalArgumentException.class, () -> user.setPin("12345"));
        assertThrows(IllegalArgumentException.class, () -> user.setPin("4534523"));


        // Check combination with letters
        assertThrows(IllegalArgumentException.class, () -> user.setPin("ad2948j4"));
        assertThrows(IllegalArgumentException.class, () -> user.setPin("bokstaver"));
        assertThrows(IllegalArgumentException.class, () -> user.setPin("1ab"));
        assertThrows(IllegalArgumentException.class, () -> user.setPin("abcd"));

        // Check empty string
        assertThrows(IllegalArgumentException.class, () -> user.setPin(""));
    }
}
