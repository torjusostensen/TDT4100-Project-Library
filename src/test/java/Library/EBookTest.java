package Library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class EBookTest {

    @Test
    void checkDownloadSizeMB() {
        // Check only valid download size can be set.
        EBook ebook = new EBook("The Hobbit", "J.Tolkien", 100, 45);

        // Quick check on getter
        assertEquals(45, ebook.getDownloadSizeMB());

        // check valid input
        ebook.setDownloadSizeMB(100);
        assertEquals(100, ebook.getDownloadSizeMB());
        ebook.setDownloadSizeMB(350);
        assertEquals(350, ebook.getDownloadSizeMB());

        // check exception is thrown for invalid input
        assertThrows(IllegalArgumentException.class, () -> ebook.setDownloadSizeMB(-1));
        assertThrows(IllegalArgumentException.class, () -> ebook.setDownloadSizeMB(-500));
        assertThrows(IllegalArgumentException.class, () -> ebook.setDownloadSizeMB(10000));
        assertThrows(IllegalArgumentException.class, () -> ebook.setDownloadSizeMB(0));
    }

    @Test
    void checkCopies() {
        // Show that the number of copies is always one through different scenarios.
        EBook ebook = new EBook("The Hobbit", "J.Tolkien", 100, 45);
        assertEquals(1, ebook.getCopies());
        ebook.setCopies(100);
        assertEquals(1, ebook.getCopies());
        ebook.setCopies(0);
        assertEquals(1, ebook.getCopies());
        ebook.setCopies(-1);
        assertEquals(1, ebook.getCopies());
        ebook.setCopies(-1000);
        assertEquals(1, ebook.getCopies());
    }
}