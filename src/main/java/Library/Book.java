package Library;


class Book {

    private String title;
    private String author;
    private int pages;
    protected int copies;

    public Book(String title, String author, int pages, int copies) {
        validateAuthor(author);
        validateTitle(title);
        validatePages(pages);
        validateCopies(copies);
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.copies = copies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int number) {
        validateCopies(number);
        this.copies = number;
    }

    public void setAuthor(String author) {
        validateAuthor(author);
        this.author = author;
    }

    public void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }

    public void setPages(int pages) {
        validatePages(pages);
        this.pages = pages;
    }

    // Private methods to check validation, delegated to ValidateMethods
    private void validateAuthor(String author) {
        ValidateMethods.checkAuthor(author);
    }

    private void validateTitle(String title) {
        ValidateMethods.checkTitle(title);
    }

    private void validatePages(int pages) {
        ValidateMethods.checkPages(pages);
    }

    private void validateCopies(int copies) {
        ValidateMethods.checkCopies(copies);
    }

    @Override
    public String toString() {
        return String.format("|Book:  %5s |Author: %5s |Pages: %2s |Size: %2s%n", this.getTitle(), this.getAuthor(), this.getPages(), "");
    }
}