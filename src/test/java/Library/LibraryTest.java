package Library;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LibraryTest {

    Library library = new Library();
    ReadAndWrite confirmation = new ReadAndWrite();
    User superUser = new User("99999999", "9999");
    User user = new User("44444444", "4444");

    Book book1 = new Book("Hello", "W.Orld", 50, 4);
    Book book2 = new Book("Hobbit", "J.Tolkien", 100, 8);
    EBook ebook1 = new EBook("Bye", "W.Orld", 40, 32);
    EBook ebook2 = new EBook("Hobbit", "J.Tolkien", 34, 45);

    @Test
    void testConstructor() {
        assertNotNull(library);
        assertEquals(0, (library.getBookInventory().size()) );
        assertEquals(0, (library.getBookInventory().size()) );
        assertEquals(0, (library.getYourBookChart().size()) );
    }

    @Test
    void addBookToLibrary() {
        // Check exception is thrown when adding books without logged in. 
        assertThrows(IllegalStateException.class, () -> library.addBookToLibrary(book1));
        assertThrows(IllegalStateException.class, () -> library.addBookToLibrary(ebook1));
        assertEquals(0, library.getBookInventory().size());
        library.superUserLogin(superUser);
        library.addBookToLibrary(book1);
        library.addBookToLibrary(ebook1);
        // Check that the books are added to the library.
        assertEquals(2, library.getBookInventory().size());
        library.superUserLogout();
        // Check exception is thrown when adding books after logging out.
        assertThrows(IllegalStateException.class, () -> library.addBookToLibrary(ebook2));

        library.superUserLogin(superUser);
        library.addUser("45454545", "4545");
        library.superUserLogout();
        library.loginUser("45454545", "4545");
        assertTrue(library.getIsUser());

        // Check regular user cannot add books.
        assertThrows(IllegalStateException.class, () -> library.addBookToLibrary(ebook2));
        library.logoutUser();
        assertFalse(library.getIsUser());
    }

    @Test
    void removeLastBookFromLibrary() {
        // Check cannot remove books without logging in.
        assertThrows(IllegalStateException.class, () -> library.removeLastBookFromLibrary());
        library.superUserLogin(superUser);
        library.addBookToLibrary(book1);
        library.addBookToLibrary(ebook1);
        library.addBookToLibrary(book2);
        assertEquals(3, library.getBookInventory().size());
        library.removeLastBookFromLibrary();
        assertEquals(2, library.getBookInventory().size());
        library.removeLastBookFromLibrary();
        assertEquals(1, library.getBookInventory().size());
        library.removeLastBookFromLibrary();
        assertEquals(0, library.getBookInventory().size());
        // Check exception is thrown when removing books from empty library. 
        assertThrows(IllegalStateException.class, () -> library.removeLastBookFromLibrary());
    }

    @Test
    void superUserLogin() {
        // check exception is thrown when trying to login without logging in as admin.
        assertThrows(IllegalArgumentException.class, () -> library.superUserLogin(user));
        library.superUserLogin(superUser);
        // Check logged in as admin.
        assertTrue(library.getIsSuperUser());
        // Check exception is thrown when trying to login when already logged in as admin.
        assertThrows(IllegalArgumentException.class, () -> library.superUserLogin(user));
        library.superUserLogout();
    }

    @Test
    void superUserLogout() {
        // Check exception is thrown when trying to logout without logging in as admin.
        assertThrows(IllegalStateException.class, () -> library.superUserLogout());

        library.superUserLogin(superUser);
        library.addUser("44444444", "4444");
        library.superUserLogout();
        library.loginUser("44444444", "4444");
        // Check exception is thrown when trying to logout when logged in as regular user.
        assertThrows(IllegalStateException.class, () -> library.superUserLogout());

        // Check login as admin works.
        library.superUserLogin(superUser);
        assertTrue(library.getIsSuperUser());

        // Check logout works.
        library.superUserLogout();
        assertFalse(library.getIsSuperUser());
        assertThrows(IllegalStateException.class, () -> library.superUserLogout());
    }

    @Test
    void addUser() {
        // Check exception is thrown when adding users without logging in as admin.
        assertThrows(IllegalArgumentException.class, () -> library.addUser("454647647", "1234"));
        library.superUserLogin(superUser);
        // Confirm no one is added.
        assertEquals(0, library.getCustomers().size());
        library.addUser("45454545", "1234");
        // Check user is added.
        assertEquals(1, library.getCustomers().size());
        // Check exception is thrown when adding users after logging out as admin.
        assertThrows(IllegalArgumentException.class, () -> library.addUser("45454545", "1234"));

        // Check regular user cannot add users.
        library.addUser("99454545", "1234");
        library.superUserLogout();
        library.loginUser("99454545", "1234");
        assertTrue(library.getIsUser());
        assertDoesNotThrow(() -> library.addUser("99889988", "1234"));
    }

    @Test
    void loginUser() {
        library.superUserLogin(superUser);
        assertEquals(0, library.getCustomers().size());
        // Check non-existing user cannot log in
        assertThrows(IllegalArgumentException.class, () -> library.loginUser("45454545", "1234"));
        library.addUser("45454545", "1234");
        library.loginUser("45454545", "1234");
        // Check user is logged in
        assertTrue(library.getIsUser());
        assertEquals(1, library.getCustomers().size());
        // Check exception is thrown when phone number is not registered.
        assertThrows(IllegalArgumentException.class, () -> library.loginUser("93454545", "1234"));
        assertThrows(IllegalArgumentException.class, () -> library.loginUser("45454545", "12345"));
        library.superUserLogout();
    }

    @Test
    void logoutUser() {

        // Check exception is thrown when trying to logout without logging in as user.
        assertThrows(IllegalStateException.class, () -> library.logoutUser());
        library.superUserLogin(superUser);
        library.addUser("45454545", "1234");
        library.loginUser("45454545", "1234");

        // Confirm user is logged in
        assertTrue(library.getIsUser());
        library.logoutUser();
        assertFalse(library.getIsUser());

        // Check exception is thrown when trying to logout after logging out as user.
        assertThrows(IllegalStateException.class, () -> library.logoutUser());
    }

    @Test
    void addBookToChart() {
        // Check if book cannot be added to chart when not in library.
        assertThrows(IllegalStateException.class, () -> library.addBookToChart(book1));
        library.superUserLogin(superUser);
        library.addBookToLibrary(book1);
        // Check exception is thrown user tries to add book to chart when not existing in library.
        assertThrows(IllegalStateException.class, () -> library.addBookToChart(book1));

        library.addUser("45454545", "1234");
        library.loginUser("45454545", "1234");
        assertTrue(library.getBookInventory().contains(book1));
        book1.setCopies(5);
        assertFalse(library.getYourBookChart().contains(book1));
        library.addBookToChart(book1);
        // Check exception is thrown when book is already in chart.
        assertThrows(IllegalStateException.class, () -> library.addBookToChart(book1));
        assertEquals(1, library.getYourBookChart().size());
        library.superUserLogout();
    }

    @Test
    void emptyChart() {
        library.superUserLogin(superUser);
        library.addUser("45454545", "1234");
        library.loginUser("45454545", "1234");
        library.addBookToChart(book1);
        assertEquals(1, library.getYourBookChart().size());
        library.emptyChart();
        // Check chart is emptied.
        assertEquals(0, library.getYourBookChart().size());
        // Check exception is thrown when book is not in chart.
        library.superUserLogout();
    }

    @Test
    void loanBooks() {
        library.superUserLogin(superUser);
        library.addUser("45454545", "1234");
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        library.superUserLogout();
        library.loginUser("45454545", "1234");
        library.addBookToChart(book1);
        library.addBookToChart(book2);
        library.loanBooks(library.getYourBookChart());

        // Check if the lists is updated after loan.
        assertEquals(0, library.getYourBookChart().size());
        assertEquals(2, library.getBookInventory().size());
        assertEquals(2, library.getBorrowedBooks().size());
        // Check exception is thrown when empty chart.
        assertThrows(IllegalStateException.class, () -> library.loanBooks(library.getYourBookChart()));
        library.logoutUser();
        // Check exception is thrown when not logged in as user.
        assertThrows(IllegalStateException.class, () -> library.loanBooks(library.getYourBookChart()));
    }

    @Test
    void returnBooks() {
        library.superUserLogin(superUser);
        library.addUser("45454545", "1234");
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        library.loginUser("45454545", "1234");
        library.addBookToChart(book1);
        library.addBookToChart(book2);
        library.loanBooks(library.getYourBookChart());
        library.returnBooks(library.getBorrowedBooks());

        // Check if the lists is updated after return.
        assertEquals(0, library.getYourBookChart().size());
        assertEquals(2, library.getBookInventory().size());
        assertEquals(0, library.getBorrowedBooks().size());
        // Check exception is thrown when empty chart.
        assertThrows(IllegalStateException.class, () -> library.returnBooks(library.getYourBookChart()));
        library.logoutUser();
        // Check exception is thrown when not logged in as user.
        assertThrows(IllegalStateException.class, () -> library.returnBooks(library.getYourBookChart()));
    }

    @Test
    void restockLowStock() {
        library.superUserLogin(superUser);
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        // Check copies before restock
        assertEquals(4, book1.getCopies());
        assertEquals(8, book2.getCopies());
        library.restockLowStock();
        // Check copies after restock
        assertEquals(2, library.getBookInventory().size());
        assertEquals(9, book1.getCopies());
        assertEquals(8, book2.getCopies());
    }

    @Test
    void getBookInventory() {
        // Check if list is empty at start.
        assertEquals(0, library.getBookInventory().size());
        library.superUserLogin(superUser);
        library.addBookToLibrary(book1);
        library.addBookToLibrary(book2);
        // Check if list is updated after adding books.
        assertEquals(2, library.getBookInventory().size());
    }
}