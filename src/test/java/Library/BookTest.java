package Library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void checkAuthor() {
        Book book = new Book("The Hobbit", "J.Tolkien", 45, 2);
        // Quick check on the getter
        assertEquals("J.Tolkien", book.getAuthor());
        assertTrue(assertThrows(IllegalArgumentException.class, () -> {
            book.setAuthor("");
        }).getMessage().contains("Author cannot be empty"));
        // Check if the exception is thrown when the author is incorrect
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(""));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(" J.Tolkien"));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor("J.Tolkien "));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor("J.Tolkien J.Tolkien"));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor("J.Tolkien45"));
    }

    @Test
    void checkTitle() {
        Book book = new Book("The Hobbit", "J.Tolkien", 45, 2);
        // Quick check on the getter
        assertEquals("The Hobbit", book.getTitle());

        // Check if the exception is thrown when the title is incorrect
        assertThrows(IllegalArgumentException.class, () -> book.setTitle(""));
        assertThrows(IllegalArgumentException.class, () -> book.setTitle(" The Hobbit"));
        assertThrows(IllegalArgumentException.class, () -> book.setTitle("  The Hobb  it "));
        assertThrows(IllegalArgumentException.class, () -> book.setTitle("  The Hobbit 234The Hobbit"));
        assertThrows(IllegalArgumentException.class, () -> book.setTitle("The Hobbit45"));
    }

    @Test
    void checkPages() {
        Book book = new Book("The Hobbit", "J.Tolkien", 45, 2);
        // Quick check on the getter
        assertEquals(45, book.getPages());

        // Check if the exception is thrown when the pages are incorrect
        assertThrows(IllegalArgumentException.class, () -> book.setPages(-4));
        assertThrows(IllegalArgumentException.class, () -> book.setPages(-1));
        assertThrows(IllegalArgumentException.class, () -> book.setPages(0));
        book.setPages(46);
        assertEquals(46, book.getPages());
    }

    @Test
    void checkCopies() {
        Book book = new Book("The Hobbit", "J.Tolkien", 45, 2);
        // Quick check on the getter
        assertEquals(2, book.getCopies());

        // Check if the exception is thrown when the copies are incorrect
        assertThrows(IllegalArgumentException.class, () -> book.setCopies(-4));
        assertThrows(IllegalArgumentException.class, () -> book.setCopies(-1));
        book.setCopies(3);
        assertEquals(3, book.getCopies());
        book.setCopies(3);
        assertEquals(3, book.getCopies());
    }
}