package Library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReadAndWriteTest {

    // Using @TempDir, which is a temporary directory to test file writing and reading which avoid dealing with many test files.
    // https://junit.org/junit5/docs/5.4.2/api/org/junit/jupiter/api/io/TempDir.html

    Library library = new Library();
    ReadAndWrite bookingConfirmation = new ReadAndWrite();
    User superUser = new User("99999999", "9999");

     // combined read and write together as both requires reading and writing to files

    @Test
    void readAndWriteFileWithTemporaryFile(@TempDir Path directory) throws IOException {
        Path test = directory.resolve("12345.txt");

        // check exception when empty filename is given
        assertThrows(IllegalArgumentException.class, () -> bookingConfirmation.writeFile("", library));
    
        // Test if file is not found
        assertThrows(IOException.class, () -> bookingConfirmation.readFile("readFileTest.txt"));
        // Test if file is found
        Files.createFile(directory.resolve(test));
        Files.write(directory.resolve("readFileTest.txt"), Arrays.asList("This is a test"));

        library.superUserLogin(superUser);
        library.addUser("45454545", "4545");
        library.superUserLogout();
        library.loginUser("45454545", "4545");

        // WriteFile is set to print out receipt, comparing with the expected output
        bookingConfirmation.writeFile("12345", library);

        // Checks only substring as LocalDate and LocalTime in receipt will be different
        String b = bookingConfirmation.readFile("12345").substring(0, 10);
        assertEquals(b, library.toString().substring(0, 10));

        // Check Exception is thrown if file name is empty or wrong.
        assertThrows(IllegalArgumentException.class, () -> {
            bookingConfirmation.writeFile("", library);
        });

        // check exception is thrown if file name is empty or wrong.
        assertThrows(FileNotFoundException.class, () -> {
            bookingConfirmation.readFile("");
        });
    }

    @Test
    void clearFileWithTemporaryFile(@TempDir Path directory) throws IOException {
        Path test = directory.resolve("12345.txt");

        // Using a temporary file to check if file would be cleared

        List<String> testStrings = Arrays.asList("10", "20", "30");
        Files.write(test, testStrings);

        assertEquals(testStrings, Files.readAllLines(test));
        assertEquals(Files.exists(test), true);
        bookingConfirmation.clearFile("12345.txt");
        assertEquals(Files.exists(test), true);
    }
}