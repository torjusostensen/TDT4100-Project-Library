package Library;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class LibraryController {
    private Library library;

    private final ReadAndWrite bookingConfirmation = new ReadAndWrite();

    @FXML
    private Button addButton, deliverBook, exitButton,
            getReceipt, loanBook, loginButton, removeButton, restockButton, userButton,
            userExit, adminButton, newUser;
    @FXML
    private Pane adminPane, chartPane, userPane, loginPane;
    @FXML
    private TextField authorField, copiesField, pagesField, phoneNumber, sizeField, titleField;
    @FXML
    private FlowPane bookShelf;
    @FXML
    private CheckBox chooseBook;
    @FXML
    private Label numberOfBooks, priceBooks, statusLabel;
    @FXML
    private PasswordField pinCode;
    @FXML
    private ListView<Book> yourChart;

    // Initialize library and lists
    @FXML
    private void initialize() {
        library = initializeLibrary();
        initializeAvailableBooks();
        updateBookChart();
        adminPane.setVisible(false);
        bookShelf.setVgap(10);
        loginDeactivated();

        for (Book book : library.getBookInventory()) {
            bookShelf.getChildren().add(createButton(book));
        }
    }

    @FXML
    private Library initializeLibrary() {
        library = new Library();
        return library;
    }

    @FXML
    private void initializeAvailableBooks() {
        library.superUserLogin(new User("99999999", "9999"));
        library.addBookToLibrary(new Book("Wonderful Life", "J.Adams", 350, 3));
        library.addBookToLibrary(new Book("The Great Gatsby", "F.Scott", 250, 5));
        library.addBookToLibrary(new Book("The Hobbit", "J.Tolkien", 300, 6));
        library.addBookToLibrary(new EBook("The Lord of the Rings", "J.Tolkien", 400, 350));
        library.addBookToLibrary(new EBook("Hello World", "J.Tolkien", 400, 350));
        library.addBookToLibrary(new EBook("Book Overflow", "J.Tolkien", 400, 350));
        library.superUserLogout();
    }

    // admin functions
    @FXML
    private void handleLogin() {
        try {
            library.loginUser(phoneNumber.getText(), pinCode.getText());
            loginActivated();
            statusLabel.setText("Welcome back!");
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Invalid credentials");
        } catch (IllegalStateException e) {
            statusLabel.setText("User not found");
        } catch (Exception e) {
            statusLabel.setText("Something went wrong");
        }
    }

    @FXML
    private void handleNewUser() {
        try {
            library.addUser(phoneNumber.getText(), pinCode.getText());
            statusLabel.setText("User added");
            loginActivated();
        } catch (IllegalStateException e) {
            statusLabel.setText("User already exists");
        } catch (Exception e) {
            statusLabel.setText("Something went wrong");
        }
    }

    @FXML
    private void handleLogout() {
        try {
            library.logoutUser();
            statusLabel.setText("User logged out");
            loginDeactivated();
            
        } catch (IllegalStateException e) {
            statusLabel.setText("User not logged in");
        } catch (Exception e) {
            statusLabel.setText("Something went wrong");
        }
    }

    @FXML
    private void handleActivateAdmin() {
        try {
            library.superUserLogin(new User(phoneNumber.getText(), pinCode.getText()));
            adminActivated();
            statusLabel.setText("Welcome admin");
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Wrong credentials");
        } catch (Exception e) {
            statusLabel.setText("Error occurred, please try again");
        }
 }

    @FXML
    private void handleExitAdmin() {
        try {
            library.superUserLogout();
            adminDeactivated();
            statusLabel.setText("Admin mode off");
        } catch (Exception e) {
            statusLabel.setText("Error occurred exciting admin mode");
        }
    }

    @FXML
    private void handleChooseTypeOfBook() {
        try {
            if (chooseBook.isSelected()) {
                sizeField.setVisible(true);
                copiesField.setVisible(false);
            } else {
                sizeField.setVisible(false);
                copiesField.setVisible(true);
            }
        } catch (Exception e) {
            statusLabel.setText("Choose type of book!");
        }
    }

    @FXML
    private void handleAddBookToLibrary() {
        try {
            // max capacity of books is 10
            if (bookShelf.getChildren().size() < 10) {
            if (chooseBook.isSelected()) {
            eBookActivated();
            EBook eBook = new EBook(titleField.getText(), authorField.getText(), Integer.parseInt(pagesField.getText()), Integer.parseInt(sizeField.getText()));
                Button button = createButton(eBook);
                library.addBookToLibrary(eBook);
                bookShelf.getChildren().add(button);
                statusLabel.setText("EBook added!");
            } else if (!chooseBook.isSelected()) {
                bookActivated();
                Book book = new Book(titleField.getText(), authorField.getText(), Integer.parseInt(pagesField.getText()), Integer.parseInt(copiesField.getText()));
                Button button = createButton(book);
                library.addBookToLibrary(book);
                bookShelf.getChildren().add(button);
                statusLabel.setText("Book added!");
                }
            } else {
                statusLabel.setText("Library is full!");
                addButton.setDisable(true);
            }
        } catch (IllegalStateException e) {
            statusLabel.setText("Something went wrong!");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Book already exists!");
            e.printStackTrace();
            }
        }

    @FXML
    private void handleRemoveLastBookFromLibrary() {
        try {
            library.removeLastBookFromLibrary();
            updateBookInventory();
            statusLabel.setText("Last book removed!");
        } catch (IllegalStateException e) {
            statusLabel.setText("No books to remove!");
        } catch (Exception e) {
            statusLabel.setText("Error occurred!");
        }
    }

    @FXML
    private void handleListOfAllCustomers() {
        try {
            showCustomers();
        } catch (Exception e) {
            statusLabel.setText("Error occurred!");
        }
    }

    @FXML
    private void updateBookChart() {
        yourChart.getItems().clear();
        for (Book book : library.getYourBookChart()) {
            yourChart.getItems().add(book);
        }
    }

    @FXML
    private void updateBookInventory() {
        bookShelf.getChildren().clear();
        for (Book book : library.getBookInventory()) {
            bookShelf.getChildren().add(createButton(book));
        }
    }

    @FXML
    private Button createButton(Book book) {
        Button button = new Button(book.toString());
        if (book.getCopies() <= 0) {
            button.disableProperty().set(true);
        }
        button.wrapTextProperty().setValue(true);
        button.setStyle("-fx-text-alignment: center;");
        button.setCursor(Cursor.HAND);
        button.setOnAction(event -> {
            
            handleAddBookToChart(book);
        });
        button.setMaxWidth(300);
        button.setMaxHeight(5);
        return button;
    }

    // Methods for books
    @FXML
    private void handleAddBookToChart(Book book) {
        try {
            library.addBookToChart(book);
            updateBookChart();
            updateBookInventory();
            numberOfBooks.setText(String.valueOf(library.getNumberOfBooksInChart()));
            statusLabel.setText("Book added to chart");
        } catch (IllegalStateException e) {
            statusLabel.setText("Book not available");
        }
    }

    @FXML
    private void handleEmptyBookChart() {
        try {
            library.emptyChart();
            updateBookChart();
            numberOfBooks.setText(String.valueOf(library.getNumberOfBooksInChart()));
            statusLabel.setText("Book chart emptied");
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error while removing book from chart");
        }
    }

    @FXML
    private void handleRestockLowStock() throws IOException {
        library.restockLowStock();
        updateBookInventory();
        statusLabel.setText("Books restocked");
    }

    @FXML
    private void handleLoanBooks() {
        try {
            library.loanBooks(yourChart.getItems());
            statusLabel.setText("Books loaned");
            handleEmptyBookChart();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            statusLabel.setText("Error while loan books");
        } catch (IllegalStateException e) {
            statusLabel.setText("No books in chart!");
        } catch (Exception e) {
            statusLabel.setText("Error occurred, please try again!");
        }
    }

    @FXML
    private void handleConfirmLoan() throws IOException {
        try {
            bookingConfirmation.clearFile(phoneNumber.getText());
            bookingConfirmation.writeFile(phoneNumber.getText(), library);
            statusLabel.setText("Loan confirmed");
            showReceipt(phoneNumber.toString());
            } catch (IOException ex) {
            statusLabel.setText("Error with file format");
        } catch (RuntimeException e) {
            statusLabel.setText("Cannot find directory for saving receipt");
        } catch (Exception e) {
            statusLabel.setText("Error occurred, please try again!");
        } finally {
            handleEmptyBookChart();
        }
    }

    @FXML
    private void handleReturnBooks() {
        try {
            library.returnBooks(library.getBorrowedBooks());
            updateBookInventory();
            statusLabel.setText("Books returned");
            bookingConfirmation.clearFile(phoneNumber.getText());
        } catch (Exception e) {
            statusLabel.setText("Error while returning books, check if you have any books in chart");
        }
    }

    @FXML
    private void confirmReturn() {
        try {
            handleReturnBooks();
            updateBookInventory();
            statusLabel.setText("Books returned");
        } catch (Exception e) {
            statusLabel.setText("Error while confirming return");
        }
    }

    @FXML
    private void readConfirmation() {
        try {
            bookingConfirmation.readFile("src/main/java/Library/confirmation.txt");
            statusLabel.setText("Confirmation read");
        } catch (IOException e) {
            statusLabel.setText("Error while reading confirmation");
        }
    }

    // Helper methods to simplify the methods above // 
    
    @FXML
    private void afterLoanChartClear() {
        library.emptyChart();
        yourChart.getItems().clear();
        numberOfBooks.setText(String.valueOf(library.getNumberOfBooksInChart()));
    }

    @FXML
    private void loginActivated() {
        adminPane.setVisible(false);
        userPane.disableProperty().setValue(false);
        bookShelf.disableProperty().setValue(false);
        loginPane.disableProperty().setValue(true);
        yourChart.disableProperty().setValue(false);
    }

    @FXML
    private void loginDeactivated() {
        adminPane.setVisible(false);
        userPane.disableProperty().setValue(true);
        bookShelf.disableProperty().setValue(true);
        loginPane.disableProperty().setValue(false);
        phoneNumber.clear();
        pinCode.clear();
        yourChart.getItems().clear();
        yourChart.disableProperty().setValue(true);
    }

    @FXML
    private void adminActivated() {
        adminPane.setVisible(true);
        sizeField.setVisible(false);
        copiesField.setVisible(true);
    }

    @FXML
    private void adminDeactivated() {
        adminPane.setVisible(false);
        phoneNumber.clear();
        pinCode.clear();
        yourChart.getItems().clear();
        yourChart.disableProperty();
    }

    @FXML
    private void bookActivated() {
        sizeField.setVisible(false);
        copiesField.setVisible(true);
    }

    @FXML
    private void eBookActivated() {
        sizeField.setVisible(true);
        copiesField.setVisible(false);
    }

    @FXML
    private void showReceipt(String filename) throws IOException {
        // method which creates pop up window with txt file
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Receipt");
        dialog.setHeaderText("Receipt");
        dialog.setContentText(bookingConfirmation.readFile(phoneNumber.getText()));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.showAndWait();
    }

    @FXML
    private void showCustomers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
            for (User user : library.getCustomers()) {
                stringBuilder.append(user.getPhoneNumber()).append("\n");
            }
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("List of all customers");
            dialog.setHeaderText("List of all customers");
            dialog.setContentText(stringBuilder.toString());
            ButtonType buttonTypeOk = new ButtonType("OK", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
            dialog.showAndWait();
    }
}