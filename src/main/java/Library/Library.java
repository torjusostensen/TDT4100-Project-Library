package Library;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {
    
    private List<Book> bookInventory;
    private List<Book> yourBookChart;
    private List<User> customers;
    private User loggedInUser;
    private Map<User, List<Book>> userBookMap;

    private boolean isSuperUser;
    private boolean isUser;

    private User superUser = new User("99999999", "9999");

    public Library() {
        this.bookInventory = new ArrayList<>();
        this.yourBookChart = new ArrayList<>();
        this.userBookMap = new HashMap<>();
        this.customers = new ArrayList<>();
        this.isSuperUser = false;
        this.isUser = false;
    }

    public void addBookToLibrary(Book book) {
        if (checkAddBookToLibrary(book)) {
            bookInventory.add(book);
        } else {
            throw new IllegalStateException("Book exists, not admin or max books reached");
        }
    }

    public void removeLastBookFromLibrary() {
        if (checkRemoveLastBookFromLibrary()) {
            bookInventory.remove(bookInventory.size() - 1);
        } else {
            throw new IllegalStateException("No books in library or not admin");
        }
    }

    public void superUserLogin(User user) {
        if (checkSuperUserLogin(user)) {
            this.isSuperUser = true;
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void superUserLogout() {
        if (isSuperUser) {
            this.isSuperUser = false;
        } else {
            throw new IllegalStateException("Not logged in as admin");
        }
    }

    public void addUser(String phoneNumber, String pin) {
        if (checkNewUser(phoneNumber)) {
            loggedInUser = new User(phoneNumber, pin);
            customers.add(loggedInUser);
            userBookMap.put(loggedInUser, new ArrayList<>());
            this.isUser = true;
        } else {
            throw new IllegalArgumentException("User already exists");
        }
    }

    public void loginUser(String phoneNumber, String pin) {
        if (checkLoginUser(phoneNumber, pin)) {
            loggedInUser = customers.stream()
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .get();
            this.isUser = true;
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void logoutUser() {
        if (isUser) {
            yourBookChart.clear();
            this.loggedInUser = null;
            this.isUser = false;
        } else {
            throw new IllegalStateException("Not logged in as user");
        }
    }

    public void addBookToChart(Book book) throws IllegalStateException {
        if (checkAddBookToChart(book)) {
            yourBookChart.add(book);
        } else {
            throw new IllegalStateException("Error adding book to chart");
        }
    }

    public void emptyChart() {
        yourBookChart.clear();
    }

    public void loanBooks(List<Book> yourBookChart) {
        if (checkLoanBooks()) {
            Iterator<Book> iterator = yourBookChart.iterator();
            while (iterator.hasNext()) {
                    Book book = iterator.next();
                    iterator.remove();
                    userBookMap.get(loggedInUser).add(book);
                    book.setCopies(book.getCopies() - 1);
                }
            } else {
                throw new IllegalStateException("No books loaned");
            }
        }

    public void returnBooks(List<Book> borrowedBooks) {
        if (checkReturnBooks()) {
            Iterator<Book> iterator = borrowedBooks.iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                iterator.remove();
                if (checkIfBookExists(book)) {
                    book.setCopies(book.getCopies() + 1);
                } else {
                    bookInventory.add(book);
                } }
        } else {
            throw new IllegalStateException("No books loaned or not logged in");
        }
    }

    // Sort objects by type of book and alphabet to make the list of books in the library easier to manage.
    public List<Book> getSortByType() {
        return userBookMap.get(loggedInUser).stream()
                .sorted(Comparator.comparing(Object::toString))
                .collect(Collectors.toList());
    }

    // Restock method which uses a Predicate to find books with low stock and add five copies.
    public void restockLowStock() {
        Predicate<Book> noSmallerThan = book -> book.getCopies() < 5;
        for (Book book : bookInventory) {
            if (noSmallerThan.test((Book) book)) {
                book.setCopies(book.getCopies() + 5);
            }
        }
    }

    // getters //
    public List<Book> getBookInventory() {
        return bookInventory;
    }

    public List<Book> getBorrowedBooks() {
        return userBookMap.get(loggedInUser);
    }

    public List<Book> getYourBookChart() {
        return yourBookChart;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public boolean getIsSuperUser() {
        return isSuperUser;
    }

    public boolean getIsUser() {
        return isUser;
    }

    public int getNumberOfBooksInChart() {
        return yourBookChart.stream().toList().size();
    }

    @Override
    public String toString() {
        String result = """
                ---------------------------------------------------
                                     JAVABRARY
                ---------------------------------------------------
                          Thank you for using our library!
                           Your booking is now confirmed!
                ---------------------------------------------------
                """ + "Last updated: " + LocalDate.now() + " " + LocalTime.now().withNano(0).toString() +"\n" + "\n";
        for (Book book : getSortByType()) {
            result += book.toString() + "\n";
        }
        result += """
                Book(s) in your chart:\040""" + userBookMap.get(loggedInUser).size() + "\n";
        result += """
                ---------------------------------------------------
                  Your books will be delivered by""" + " " + LocalDate.now().plusDays(7) + "\n";
        result += """
                ---------------------------------------------------
                """ + "\n";
        return result;
    }


    // End of public methods and beginning of private helper methods to make the code more readable.
    private boolean checkSuperUserLogin(User user) {
        return user.getPhoneNumber().equals(superUser.getPhoneNumber()) && user.getPin().equals(superUser.getPin());
    }

    private boolean checkIfBookExists(Book book) {
        for (Book b : bookInventory) {
            if (b.getTitle().equals(book.getTitle())) {
                return true;
            };
        }
        return false;
    }

    private boolean checkAddBookToLibrary(Book newBook) {
        return isSuperUser && !checkIfBookExists(newBook);
    }

    private boolean checkRemoveLastBookFromLibrary() {
        return !bookInventory.isEmpty() && isSuperUser;
    }

    private boolean checkLoginUser(String phoneNumber, String pin) {
        for (User customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                if (customer.getPin().equals(pin)) {
                    return true;
                }
                throw new IllegalArgumentException("Wrong pin!");
            }
        }
       return false;
    }

    private boolean checkNewUser(String phoneNumber) {
   
        if (phoneNumber.equals(superUser.getPhoneNumber())) {
            throw new IllegalArgumentException("Admin is not a regular user");
        }
        for (User customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAddBookToChart(Book book) {
        return (book.getCopies() > 0 && isUser && !yourBookChart.contains(book));
        
    }

    private boolean checkLoanBooks() {
        return !yourBookChart.isEmpty() && isUser;
    }

    private boolean checkReturnBooks() {
        return isUser&& !userBookMap.get(loggedInUser).isEmpty();
    }

    public static void main(String[] args) throws IOException {

        Library library = new Library();
        ReadAndWrite readAndWrite = new ReadAndWrite();

        // superuser login
        library.superUserLogin(new User("99999999", "9999"));
        Book book1 = new Book("Hello", "J.Hello", 45, 3);
        library.addBookToLibrary(book1);
        library.addUser("45454545", "4545");
        library.loginUser("45454545", "4545");
        library.addBookToChart(book1);
        library.loanBooks(library.getYourBookChart());
        readAndWrite.writeFile("232", library);
    }
}