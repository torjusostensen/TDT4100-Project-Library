package Library;

// Placed all validation methods in one class to make it easier to read, which is delegated each method to the appropriate class

class ValidateMethods {

    // Validation for book
    public static void checkAuthor(String author) {
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        if (author.indexOf(" ") == 0 || author.indexOf(" ") == author.length() - 1) {
            throw new IllegalArgumentException("Author cannot start with a space or end with a space");
        }
        if (!author.matches("[A-Z.a-z]+")) {
            throw new IllegalArgumentException("Author must contain only letters or dots, in the format of Initials.Surname");
        }
    }

    public static void checkTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.indexOf(" ") == 0 || title.indexOf(" ") == title.length() - 1) {
            throw new IllegalArgumentException("Title cannot start with a space or end with a space");
        }
        if (!title.matches("[A-Z.a-z ]+")) {
            throw new IllegalArgumentException("Title must contain only letters and dots");
        }
    }

    public static void checkPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Pages cannot be negative");
        }
    }

    public static void checkCopies(int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("Copies cannot be negative");
        }
    }

    // Validation for EBook
    public static void checkDownloadSize(int downloadSizeMB) {
        if (downloadSizeMB <= 0) {
            throw new IllegalArgumentException("Download size cannot be negative");
        }
        if (downloadSizeMB > 1000) {
            throw new IllegalArgumentException("Too large file, download size cannot be more than 1000MB");
        }
    }

    // Validation for User
    public static void checkPin(String pin) {
        if (pin.length() != 4) {
            throw new IllegalArgumentException("Pin must be exactly 4 digits");
        } else if (!pin.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Pin must contain only digits");
        }
    }

    public static void checkPhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("(^4|^9)\\d{7}")){
            throw new IllegalArgumentException("Phone number must be in the format 4xxxxxxx or 9xxxxxxx");
        }
    }
}