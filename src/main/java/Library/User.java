package Library;

import java.util.Comparator;

public class User implements Comparator<User> {

    private String pin;
    private String phoneNumber;

    public User(String phoneNumber, String pin) {
        validatePhoneNumber(phoneNumber);
        validatePin(pin);
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public void setPin(String pin) {
        validatePin(pin);
        this.pin = pin;
    }

    // Private methods to check validation
    private static void validatePin(String pin) {
        ValidateMethods.checkPin(pin);
    }

    private static void validatePhoneNumber(String phoneNumber) {
        ValidateMethods.checkPhoneNumber(phoneNumber);
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", pin='" + pin.hashCode() + '\'' +
                '}';
    }

    // Compare methods
    @Override
    public int compare(User u1, User superuser) {
        return u1.pin.compareTo(superuser.pin);
    }
}